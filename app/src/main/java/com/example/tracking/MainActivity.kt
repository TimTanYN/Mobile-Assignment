package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton

class MainActivity :BaseNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbarAndNavigation()
        diseaseNavigate()


    }


    private fun diseaseNavigate(){
        val imageButton: ImageButton = findViewById(R.id.diesase)
        imageButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }



}


