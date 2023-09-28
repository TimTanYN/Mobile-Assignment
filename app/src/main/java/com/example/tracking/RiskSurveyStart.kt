package com.example.tracking

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class RiskSurveyStart: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_start)

        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener(){
            val intent = Intent(this, RiskSurvey::class.java)
            startActivity(intent)
        }
    }
}