package com.example.tracking.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tracking.R
import com.example.tracking.model.ListViewMedicine
import com.example.tracking.model.ListViewMedicineDetails
import com.example.tracking.model.ListViewModel
import java.lang.Integer.parseInt

class ListViewMedicineDetails(val context: Context, private val dataSource:  List<ListViewMedicineDetails.ListItems>) : BaseAdapter(){
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.pharmacy, parent, false)


        val pharmacyName = rowView.findViewById<TextView>(R.id.pharmacyName)
        val image = rowView.findViewById<ImageView>(R.id.pharmacyLogo)
        val tag = rowView.findViewById<TextView>(R.id.pharmacyTag)


        val itemData = getItem(position) as com.example.tracking.model.ListViewMedicineDetails.ListItems
        tag.text= itemData.price.toString()
        pharmacyName.text = itemData.medicineName
        image.setImageResource(itemData.imageResId)

        return rowView
    }
}