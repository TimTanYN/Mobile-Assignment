package com.example.tracking.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.tracking.R
import com.example.tracking.model.ListViewDoctorList
import com.example.tracking.model.ListViewMedicine
import java.util.Locale

class ListViewDoctorList(private val context: Context,items: List<ListViewDoctorList.ListItem>): BaseAdapter(),
    Filterable {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var originalData: List<ListViewDoctorList.ListItem>
    var filteredData: List<ListViewDoctorList.ListItem>

    init {

        this.originalData = items
        this.filteredData = items
    }

    override fun getCount(): Int = filteredData.size

    override fun getItem(position: Int): Any = filteredData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.activity_doctor_list_item, parent, false)

        val doctorNameTextView = rowView.findViewById<TextView>(R.id.doctorname)
        val doctorBioTextView = rowView.findViewById<TextView>(R.id.doctorbio)
        val doctorPicImageView = rowView.findViewById<ImageView>(R.id.doctorProfile)
        val listItem = getItem(position) as ListViewDoctorList.ListItem

        doctorNameTextView.text = listItem.doctorName
        doctorBioTextView.text = listItem.doctorBio  // Corrected the attribute name
        if (listItem.imageUrl is String) {
            Glide.with(context)
                .load(listItem.imageUrl as String)
                .into(doctorPicImageView)
        } // Note: We've removed the else case since imageResId should be a string (URL) in this context

        return rowView
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.values = originalData
                    filterResults.count = originalData.size
                } else {
                    val searchText = constraint.toString().toLowerCase(Locale.getDefault()) // Added Locale
                    val filteredItems = originalData.filter {
                        it.doctorName.toLowerCase(Locale.getDefault()).contains(searchText) ||
                                it.doctorBio.toLowerCase(Locale.getDefault()).contains(searchText)
                    }
                    filterResults.values = filteredItems
                    filterResults.count = filteredItems.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    filteredData = results.values as List<ListViewDoctorList.ListItem>
                    notifyDataSetChanged()
                }
            }
        }
    }
}