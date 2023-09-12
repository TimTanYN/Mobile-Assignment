package com.example.tracking.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tracking.R
import com.example.tracking.model.ListViewModel


class ListViewAdapter (private val context: Context, private val dataSource: List<ListViewModel.ListItem>): BaseAdapter() {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.disease_list, parent, false)

        val pharmcyName = rowView.findViewById<TextView>(R.id.pharmacyName)
        val pharmacyLogo = rowView.findViewById<ImageView>(R.id.pharmacyLogo)
        val listItem = getItem(position) as ListViewModel.ListItem

        pharmcyName.text = listItem.text
        pharmacyLogo.setImageResource(listItem.imageResId)

        return rowView
    }
}