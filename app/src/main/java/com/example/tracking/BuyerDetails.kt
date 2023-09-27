package com.example.tracking

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BuyerDetails :BaseNavigationActivity(){
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buyer_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        initToolbarAndNavigation()
        val button = findViewById<Button>(R.id.buttonNext)
        button.setOnClickListener(){
            getDetails()
            val intent = Intent(this, Receipt::class.java)
            startActivity(intent)
        }

    }

    fun getDetails(){
        val name = findViewById<EditText>(R.id.name).text.toString().trim()
        val email = findViewById<EditText>(R.id.email).text.toString().trim()
        val address = findViewById<EditText>(R.id.address).text.toString().trim()

        val nameEditText = findViewById<EditText>(R.id.name)
        val emailEditText = findViewById<EditText>(R.id.email)
        val addressEditText = findViewById<EditText>(R.id.address)

        if (name.isEmpty()) {
            nameEditText.error = "Name cannot be empty"
            nameEditText.requestFocus()
            return
        }

        if (email.isEmpty()) {
            emailEditText.error = "Email cannot be empty"
            emailEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Please enter a valid email"
            emailEditText.requestFocus()
            return
        }

        if (address.isEmpty()) {
            addressEditText.error = "Address cannot be empty"
            addressEditText.requestFocus()
            return
        }

        sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("address", address)
        editor.apply()
    }
}