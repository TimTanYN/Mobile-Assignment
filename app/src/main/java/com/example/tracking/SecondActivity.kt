package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.widget.Button


class SecondActivity : BaseNavigationActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        initToolbarAndNavigation()
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