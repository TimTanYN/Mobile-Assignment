package com.example.myapplication

import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng

class DetailActivity : BaseNavigationActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        initToolbarAndNavigation()

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
}