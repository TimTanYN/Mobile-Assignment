package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar

class MainActivity :BaseNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        println("hi")
        supportActionBar?.title = "Main Activity"
        initToolbarAndNavigation()
        diseaseNavigate()
        medicineNavigate()


    }


    private fun diseaseNavigate(){
        val imageButton: ImageButton = findViewById(R.id.diesase)
        imageButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    private fun medicineNavigate(){
        val imageButton: ImageButton = findViewById(R.id.medicine)
        imageButton.setOnClickListener {
            val intent = Intent(this, MedicineActivity::class.java)
            startActivity(intent)
        }
    }


}


