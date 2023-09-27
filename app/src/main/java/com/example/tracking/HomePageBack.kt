package com.example.tracking

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class HomePageBack :BaseNavigationActivity(){

    private lateinit var suggestionTextView: TextView

    // Mock list of health suggestions
    private val suggestions = listOf(
        "Drink at least 8 glasses of water today.",
        "Take a 10-minute walk after lunch.",
        "Try a new fruit or vegetable today.",
        "Practice deep breathing exercises for relaxation.",
        "Limit screen time 1 hour before bedtime."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page_back)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Backend"
        initToolbarAndNavigation()


        suggestionTextView = findViewById(R.id.suggestionTextView)
        val refreshButton: Button = findViewById(R.id.refreshButton)

        // Display a random suggestion on app launch
        displayRandomSuggestion()

        // Refresh button click listener
        refreshButton.setOnClickListener {
            displayRandomSuggestion()
        }

    }

    private fun displayRandomSuggestion() {
        val randomSuggestion = suggestions.random()
        suggestionTextView.text = randomSuggestion
    }
}