package com.example.tracking

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.adapter.RecyclerViewAdapter
import com.example.tracking.model.RecyclerViewModel
import com.google.android.gms.maps.model.LatLng

class DetailActivity : BaseNavigationActivity(){

    var receivedLatLng = LatLng(0.0,0.0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Details"
        initToolbarAndNavigation()
        val latitude = intent.getDoubleExtra("latitude", 0.0) // default value 0.0
        val longitude = intent.getDoubleExtra("longitude", 0.0) // default value 0.0
        receivedLatLng = LatLng(latitude, longitude)
        fetchAddress(receivedLatLng)
        recyclerView()

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
//            Toast.makeText(this, "Error fetching address. Please try again.", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}", Toast.LENGTH_SHORT).show()
        }
    }

    fun recyclerView(){
        val myRecyclerView: RecyclerView = findViewById(R.id.myRecyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        val items = listOf(
            RecyclerViewModel.ItemData(R.drawable.aklogo_1, "This is some information."),
            RecyclerViewModel.ItemData(R.drawable.aklogo_1, "Info 2"),
            RecyclerViewModel.ItemData(R.drawable.aklogo_1, "Info 3"),
            // ... add more items
        )
        val adapter = RecyclerViewAdapter(items)
        myRecyclerView.adapter = adapter
    }

}