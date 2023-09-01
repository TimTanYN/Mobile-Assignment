package com.example.tracking

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchLocation(searchEditText.text.toString())
                hideKeyboard(searchEditText)
                true
            } else {
                false
            }
        }

        val myButton: Button = findViewById(R.id.myButton)
        myButton.setOnClickListener {
            val intent = Intent(this, DiseaseActivity::class.java)

            // Assuming currentMarker holds the latest LatLng value
            val latLng = currentMarker?.position
            if (latLng != null) {
                intent.putExtra("latitude", latLng.latitude)
                intent.putExtra("longitude", latLng.longitude)
            }

            startActivity(intent)
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val kl = LatLng(3.1319, 101.6841)
        mMap.addMarker(MarkerOptions().position(kl).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kl))

        mMap.setOnMapLongClickListener { latLng ->
            // This block of code will be executed when the user long-clicks on the map
            placeMarkerAndRetrieveLocation(latLng)
        }
    }

    private fun placeMarkerAndRetrieveLocation(latLng: LatLng) {
        // Clear existing markers
        mMap.clear()

        // Add marker on the chosen location
        currentMarker = mMap.addMarker(MarkerOptions().position(latLng).title("Chosen Location"))

//         Optionally: retrieve address from latLng (Requires additional setup with Geocoder)
         fetchAddress(latLng)
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
//            Toast.makeText(this, "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}", Toast.LENGTH_SHORT).show()

        }
    }

    private fun searchLocation(location: String) {
        val geocoder = Geocoder(this)
        try {
            val addressList = geocoder.getFromLocationName(location, 1)
            if (addressList != null && addressList.isNotEmpty()) {
                val address = addressList[0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                placeMarkerAndRetrieveLocation(latLng)
            } else {
                Toast.makeText(this, "Location not found!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error searching location. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
