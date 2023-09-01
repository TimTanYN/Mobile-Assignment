package com.example.tracking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.example.tracking.R
import com.example.tracking.model.ListViewMedicine
import java.util.Locale


class ListViewMedicine(private val context: Context, private val dataSource: List<ListViewMedicine.ListItem>): BaseAdapter(),
    Filterable {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val originalData: List<ListViewMedicine.ListItem> = dataSource
    private var filteredData: List<ListViewMedicine.ListItem> = ArrayList(dataSource)

    override fun getCount(): Int = filteredData.size

    override fun getItem(position: Int): Any = filteredData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.pharmacy, parent, false)

        val pharmacyName = rowView.findViewById<TextView>(R.id.pharmacyName)
        val logo = rowView.findViewById<ImageView>(R.id.pharmacyLogo)
        val tag = rowView.findViewById<TextView>(R.id.pharmacyTag)

        val itemData = getItem(position) as ListViewMedicine.ListItem

        tag.text = itemData.textViewText
        logo.setImageResource(itemData.imageResId)
        pharmacyName.text = itemData.pharmacyName

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
                    val searchText = constraint.toString().toLowerCase()
                    val filteredItems = originalData.filter {
                        it.pharmacyName.toLowerCase().contains(searchText) ||
                                it.textViewText.toLowerCase().contains(searchText)
                    }
                    filterResults.values = filteredItems
                    filterResults.count = filteredItems.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values is List<*>) {
                    filteredData = results.values as List<ListViewMedicine.ListItem>
                    notifyDataSetChanged()
                }
            }
        }
    }


}

