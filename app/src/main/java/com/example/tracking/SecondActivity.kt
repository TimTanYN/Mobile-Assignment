package com.example.tracking

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.firebase.firestore.FirebaseFirestore


class SecondActivity : BaseNavigationActivity(){
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tracking"
        initToolbarAndNavigation()
        db = FirebaseFirestore.getInstance()
//        val user = hashMapOf(
//            "first" to "John",
//            "last" to "Doe",
//            "age" to 30
//        )

        val userId = "John"  // Replace with actual user ID
        db.collection("users").document("hOaJ5VxC99abaOCSiZDZ")
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val firstName = document.getString("first")
                    val lastName = document.getString("last")
                    val age = document.getLong("age")?.toInt()

                    val userData = "First Name: $firstName, Last Name: $lastName, Age: $age"
                    findViewById<TextView>(R.id.test).text = userData
                } else {
                    Log.d("Firestore", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }


        redirect()
    }

    private fun redirect(){
        val Button: Button = findViewById(R.id.proceed)
        Button.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }
}