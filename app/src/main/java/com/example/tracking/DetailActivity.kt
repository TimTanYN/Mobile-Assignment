package com.example.tracking

import android.content.ContentValues.TAG
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.adapter.RecyclerViewAdapter
import com.example.tracking.model.RecyclerViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailActivity : BaseNavigationActivity(){

    var city = ""
    var selected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Details"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        selected = intent.getStringExtra("selected").toString()
        fetchDataFromFirestore()
        val text = findViewById<TextView>(R.id.diseases)
        text.text = selected
        city = intent.getStringExtra("city").toString()
        getAmount()

    }

    fun fetchDataFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val yourCollectionReference = db.collection(selected)
        yourCollectionReference.get()
            .addOnSuccessListener { documents ->
                val items = documents.mapNotNull { document ->
                    val text = document.getString("tips")
                    val content = document.getString("Tips1")
                    val imageResId = R.drawable.aklogo_1
                    if (text != null) {
                        RecyclerViewModel.ItemData(imageResId, text, content.toString())
                    } else {
                        null
                    }
                }
                recyclerView(items)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error fetching documents: ", exception)
            }
    }



    fun recyclerView(items: List<RecyclerViewModel.ItemData>){
        val myRecyclerView: RecyclerView = findViewById(R.id.myRecyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(items)
        myRecyclerView.adapter = adapter
    }

    fun getAmount(){
        val db = FirebaseFirestore.getInstance()
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())

        db.collection("disease")
            .document(city)
            .collection(currentDate)
            .document("disease")
           .get()
           .addOnSuccessListener { document ->
                if (document != null) {

                   val disease = document.getLong(selected)?.toInt()


                   findViewById<TextView>(R.id.amount).text = "Today Cases = " + disease.toString()
                } else {
                   Log.d("Firestore", "No such document")
                }
           }
            .addOnFailureListener { exception ->
               Log.d("Firestore", "get failed with ", exception)
           }
    }

}