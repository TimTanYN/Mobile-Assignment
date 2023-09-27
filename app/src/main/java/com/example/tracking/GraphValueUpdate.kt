package com.example.tracking

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.FirebaseDatabase
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GraphValueUpdate:BaseNavigationActivity() {
    val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph_value_update)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        data class User(val amount: Int)
        val button = findViewById<Button>(R.id.update)

        button.setOnClickListener(){
            val value = parseInt(findViewById<EditText>(R.id.value).text.toString())
            val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, 4) // Add one day to the current date
            val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val tomorrow = dateFormat.format(calendar.time)
            val user = User(value)
            database.child("disease").child(currentDate).setValue(user)
        }




    }
}