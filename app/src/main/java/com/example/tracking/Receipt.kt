package com.example.tracking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class Receipt:BaseNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receipt)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Receipt"
        initToolbarAndNavigation()

        val totalPriceTextView: TextView = findViewById(R.id.totalPriceTextView)
        val nameTextView: TextView = findViewById(R.id.nameTextView)
        val emailTextView: TextView = findViewById(R.id.emailTextView)
        val addressTextView: TextView = findViewById(R.id.addressTextView)

        // Fetch details from SharedPreferences
        val sharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val totalPrice = sharedPreferences.getLong("totalPrice", 0L)
        val name = sharedPreferences.getString("name", "")
        val email = sharedPreferences.getString("email", "")
        val address = sharedPreferences.getString("address", "")

        // Display the details
        totalPriceTextView.text = "Total Price: $totalPrice"
        nameTextView.text = "Name: $name"
        emailTextView.text = "Email: $email"
        addressTextView.text = "Address: $address"

        val home = findViewById<Button>(R.id.home)
        home.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}