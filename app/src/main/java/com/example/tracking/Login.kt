package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tracking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login :AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val login = findViewById<Button>(R.id.login)
        login.setOnClickListener(){
            login()
        }
        val signup = findViewById<Button>(R.id.signUp)
        signup.setOnClickListener(){
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

    }

    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(){


        val email = findViewById<EditText>(R.id.email).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        if (!isValidEmail(email)) {
            if (!isValidPassword(password)) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Login successful
                            val currentUser = auth.currentUser
                            val db = FirebaseFirestore.getInstance()
                            if (currentUser != null) {
                                val userDocRef = db.collection("users").document(currentUser.uid)
                                userDocRef.get()
                                    .addOnSuccessListener { document ->
                                        if (document != null && document.exists()) {
                                            val role = document.getString("role")
                                            if(role == "admin"){
                                                val intent = Intent(this, HomePageBack::class.java)
                                                startActivity(intent)
                                            }else{
                                                val intent = Intent(this, MainActivity::class.java)
                                                startActivity(intent)
                                            }
                                        } else {
                                            Log.d("UserRole", "No such document")
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.d("UserRole", "Failed to get user role: ", exception)
                                    }
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Account not found. Please register.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            // Navigate to next activity or do other tasks
                        } else {
                            // Login failed
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
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