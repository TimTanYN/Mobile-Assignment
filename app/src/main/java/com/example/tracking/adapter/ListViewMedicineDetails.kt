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
import com.example.tracking.model.ListViewMedicine
import com.example.tracking.model.ListViewMedicineDetails
import com.example.tracking.model.ListViewModel
import java.lang.Integer.parseInt
import java.util.Locale

class ListViewMedicineDetails(val context: Context, items:  List<ListViewMedicineDetails.ListItems>) : BaseAdapter(),
    Filterable {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
     var originalData: List<ListViewMedicineDetails.ListItems>
     var filteredData: List<ListViewMedicineDetails.ListItems>

    init {

        this.originalData = items
        this.filteredData = items
    }
    override fun getCount(): Int = filteredData.size

    override fun getItem(position: Int): Any = filteredData[position]

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
                        it.medicineName.toLowerCase(Locale.getDefault()).contains(searchText)
                    }
                    filterResults.values = filteredItems
                    filterResults.count = filteredItems.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    filteredData = results.values as List<ListViewMedicineDetails.ListItems>
                    notifyDataSetChanged()
                }
            }
        }
    }
}