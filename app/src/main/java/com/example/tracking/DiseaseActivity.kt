package com.example.tracking

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import com.example.tracking.adapter.ListViewAdapter
import com.example.tracking.model.ListViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TreeMap


class DiseaseActivity : BaseNavigationActivity(){
    var receivedLatLng = LatLng(0.0,0.0)
    var city = ""
    private lateinit var db :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disease)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Disease"
        initToolbarAndNavigation()
        val intent = intent
        val latitude = intent.getDoubleExtra("latitude", 0.0) // default value 0.0
        val longitude = intent.getDoubleExtra("longitude", 0.0) // default value 0.0
        city = intent.getStringExtra("city").toString()
        receivedLatLng = LatLng(latitude, longitude)
        chart()
        list()
        fetchAddress(receivedLatLng)

    }

    fun chart() {
        val lineChart: LineChart = findViewById(R.id.lineChart)
        val customColor = Color.parseColor("#007AFE")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("disease")
        val dataForChart = TreeMap<String, Long>()
        val validDates = getLastSixDates()

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dateSnapshot in dataSnapshot.children) {
                    val date = dateSnapshot.key ?: continue
                    val amountData = dateSnapshot.child("amount").value as? Long ?: continue

                    // Store date and amount in the TreeMap
                    dataForChart[date] = amountData
                }

                // Now, dataForChart contains the data in ascending order of dates.
                // Populate the chart here
                val entries = ArrayList<Entry>()
                for ((index, entry) in dataForChart.entries.withIndex()) {
                    entries.add(Entry(index.toFloat(), entry.value.toFloat()))
                }

                val dataSet = LineDataSet(entries, "Amount over Time")
                dataSet.color = customColor
                dataSet.valueTextColor = Color.RED
                dataSet.lineWidth = 2.5f
                dataSet.setCircleColor(Color.RED)
                dataSet.circleRadius = 5f
                dataSet.setDrawCircleHole(false)
                dataSet.valueTextSize = 12f

                // Design
                lineChart.description.isEnabled = false
                lineChart.setDrawGridBackground(false)
                lineChart.setTouchEnabled(true)
                lineChart.isDragEnabled = true
                lineChart.setScaleEnabled(true)
                lineChart.setPinchZoom(true)


// Remove right y-axis
                lineChart.axisRight.isEnabled = false
                lineChart.xAxis.isEnabled = false
                lineChart.axisLeft.isEnabled = false

// X-Axis design
                val xAxis = lineChart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.textSize = 12f
                xAxis.textColor = Color.BLACK
                xAxis.setDrawAxisLine(true)
                xAxis.setDrawGridLines(false)
//                xAxis.labelCount = 4
                xAxis.valueFormatter = DateValueFormatter(validDates)
                xAxis.granularity = 1f
                lineChart.xAxis.setDrawGridLines(false)
                lineChart.axisLeft.setDrawGridLines(false)
                xAxis.position = XAxis.XAxisPosition.TOP
                xAxis.isEnabled = true

// Legend design
                val legend = lineChart.legend
                legend.form = Legend.LegendForm.LINE
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

                val lineData = LineData(dataSet)
                lineChart.data = lineData
                lineChart.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle errors here
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun list(){
        val db = FirebaseFirestore.getInstance()
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val docRef = db.collection("disease")
            .document(city)
            .collection(currentDate)
            .document("disease")
        val listView: ListView = findViewById(R.id.DiseaselistView)


        docRef
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Extracting field names from the document
                    val fieldNames = documentSnapshot.data?.keys?.map {
                        ListViewModel.ListItem(it, R.drawable.btn_icon)
                    } ?: listOf()

                    // Set the fetched data to your ListView using your custom adapter

                    val adapter = ListViewAdapter(this, fieldNames)
                    listView.adapter = adapter
                }
            }

//        val items = listOf(
//            ListViewModel.ListItem("Sample text 1", R.drawable.btn_icon),
//            ListViewModel.ListItem("Sample text 2", R.drawable.btn_icon),
//            ListViewModel.ListItem("Sample text 3", R.drawable.btn_icon),
//            ListViewModel.ListItem("Sample text 4", R.drawable.btn_icon),
//            ListViewModel.ListItem("Sample text 5", R.drawable.btn_icon),
//            // ... add more items as needed
//        )
//
//        val adapter = ListViewAdapter(this, items)
//        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->

            val selectedItem = listView.adapter.getItem(position) as ListViewModel.ListItem
            Toast.makeText(this, "Selected: ${selectedItem.text}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("selected",selectedItem.text)
            startActivity(intent)
        }
        }

    private fun fetchAddress(latLng: LatLng) {
        val geocoder = Geocoder(this)
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses?.get(0)
                    // You can get more details from 'address' as per your requirement
                    if (address != null) {
                        Toast.makeText(this, address.getAddressLine(0), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Address not found!", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error fetching address. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    fun getLastSixDates(): List<String> {
        val dateFormat = SimpleDateFormat("MMdd", Locale.getDefault())
        val dates = mutableListOf<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..5) {
            dates.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }

        return dates
    }


}

class DateValueFormatter(private val dates: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value.toInt() < dates.size) dates[value.toInt()] else ""
    }
}