package com.example.tracking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.R
import com.example.tracking.model.RecyclerViewModel

class RecyclerViewAdapter(private val items: List<RecyclerViewModel.ItemData>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()  {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemText: TextView = view.findViewById(R.id.tipsTitle)
        val content: TextView = view.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.itemImage.setImageResource(item.imageResId)
        holder.itemText.text = item.text
        holder.content.text = item.content
    }

    override fun getItemCount() = items.size


}