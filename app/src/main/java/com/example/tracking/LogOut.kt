package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LogOut:BaseNavigationActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

            // Sign out the user
            auth.signOut()

            // Navigate to the login screen
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()  // Optional: remove this activity from the stack

    }

}