package com.example.tracking

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.gms.maps.model.LatLng


class DiseaseActivity : BaseNavigationActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.disease)
        initToolbarAndNavigation()
        val intent = intent
        val latitude = intent.getDoubleExtra("latitude", 0.0) // default value 0.0
        val longitude = intent.getDoubleExtra("longitude", 0.0) // default value 0.0
        val receivedLatLng = LatLng(latitude, longitude)
        chart()
        list()


    }

    fun chart(){
        val lineChart: LineChart = findViewById(R.id.lineChart)
        val customColor = Color.parseColor("#007AFE")


        // Example data
        val entries = ArrayList<Entry>()
        entries.add(Entry(0f, 1f))
        entries.add(Entry(1f, 2f))
        entries.add(Entry(2f, 3f))
        entries.add(Entry(3f, 5f))
        entries.add(Entry(4f, 3f))
        entries.add(Entry(5f, 7f))

        // Create dataset
        val dataSet = LineDataSet(entries, "Sample Data")
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
        xAxis.labelCount = 4
        xAxis.granularity = 1f
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.isEnabled = true

// Legend design
        val legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER

// Apply data
        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart.invalidate()
    }

    fun list(){
        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5","Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetailActivity::class.java)

            // Optional: if you want to send some data to the new activity
            // intent.putExtra("key", "value")

            startActivity(intent)
        }
        }


}