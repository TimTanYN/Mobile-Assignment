package com.example.tracking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.tracking.R
import com.example.tracking.model.ListViewBackendDisease

class ListViewBackendDisease(private val context: Context, private val items: List<ListViewBackendDisease.ListItem>) : ArrayAdapter<ListViewBackendDisease.ListItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.backend_disease_list, parent, false)

        val cityTextView: TextView = view.findViewById(R.id.city)
        val dateTextView: TextView = view.findViewById(R.id.date)
        val covidTextView: TextView = view.findViewById(R.id.covid)
        val coliTextView: TextView = view.findViewById(R.id.coli)
        val fluTextView: TextView = view.findViewById(R.id.flu)

        cityTextView.text = item?.city
        dateTextView.text = item?.date
        covidTextView.text = item?.covid
        coliTextView.text = item?.coli
        fluTextView.text = item?.flu

        return view
    }
}