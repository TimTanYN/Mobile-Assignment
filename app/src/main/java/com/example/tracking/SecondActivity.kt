package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class SecondActivity : BaseNavigationActivity(){
    val database = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tracking"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        initToolbarAndNavigation()



        data class User(val amount: Int)


        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 4) // Add one day to the current date
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val tomorrow = dateFormat.format(calendar.time)
        val user = User(52)
        database.child("disease").child(tomorrow).setValue(user)
        redirect()
    }

    private fun redirect(){
        val Button: Button = findViewById(R.id.proceed)
        Button.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()  // Call this method to finish the current activity and return to the previous one
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}