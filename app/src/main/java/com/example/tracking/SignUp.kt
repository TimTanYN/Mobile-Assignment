package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val register = findViewById<Button>(R.id.register)
        val login = findViewById<Button>(R.id.signin)
        register.setOnClickListener(){
            signup()
        }
        login.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signup(){

        val email = findViewById<EditText>(R.id.email).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val displayName = findViewById<EditText>(R.id.name).text.toString()
        val db = FirebaseFirestore.getInstance()

        if (isValidEmail(email)) {
            if (isValidPassword(password)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // User registration successful
                            val user = auth.currentUser

                            // 2. Update user's profile to set the display name
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(displayName)
                                .build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileTask ->
                                    if (profileTask.isSuccessful) {
                                        // Display name updated successfully
                                        Toast.makeText(baseContext, "User registered successfully.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Error updating display name
                                        Toast.makeText(baseContext, "Error setting display name.", Toast.LENGTH_SHORT).show()
                                    }
                                }


                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        val currentUser = auth.currentUser
                                        val userDocRef = currentUser?.let {
                                            db.collection("users").document(
                                                it.uid)
                                        }
                                        val data = hashMapOf(
                                            "role" to "user"
                                        )
                                        userDocRef?.set(data)?.addOnSuccessListener {
                                            Log.d("Firestore", "Document successfully written!")
                                        }?.addOnFailureListener { e ->
                                            Log.w("Firestore", "Error writing document", e)
                                        }
                                        Toast.makeText(
                                            baseContext,
                                            "Registration successful. Please check your email for verification.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        // Error sending verification email
                                        Toast.makeText(
                                            baseContext,
                                            "Error sending verification email.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            // User registration failed
                            Log.e("RegistrationError", "Failed to register user", task.exception)
                            Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(baseContext, "Registration failed.", Toast.LENGTH_SHORT).show()
        }



    }

    fun isValidEmail(email: String): Boolean {
        // Check if email is not empty and matches the regular expression for email format
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
        return email.isNotEmpty() && email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        // Check if password is not empty and meets the criteria
        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"
        return password.isNotEmpty() && password.matches(passwordRegex.toRegex())
    }




}