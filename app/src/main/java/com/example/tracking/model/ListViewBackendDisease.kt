package com.example.tracking.model

import android.widget.ListAdapter

class ListViewBackendDisease {

    data class ListItem(
        val city: String,
        val date: String,
        val covid: String,
        val coli: String,
        val flu: String
    )
}