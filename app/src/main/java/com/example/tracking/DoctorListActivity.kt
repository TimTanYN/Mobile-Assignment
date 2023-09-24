package com.example.tracking

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.tracking.model.ListViewDoctorList
import com.google.firebase.firestore.FirebaseFirestore

class DoctorListActivity : BaseNavigationActivity() {
    val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_list) // 使用正确的布局文件名

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        println("hi")
        supportActionBar?.title = "Doctor List"
        initToolbarAndNavigation()

        list()
    }

    fun list() {
        val doctorCollection = db.collection("/doctor")
        doctorCollection.get().addOnSuccessListener { querySnapshot ->
            val items = querySnapshot.documents.map { document ->
                val doctorBio = document.getString("doctorBio") ?: ""
                val doctorName = document.getString("doctorName") ?: ""
                val imageUrl = document.getString("doctorPic") ?: ""
                ListViewDoctorList.ListItem(doctorName, doctorBio, imageUrl)  // Corrected the sequence
            }
            val adapter = com.example.tracking.adapter.ListViewDoctorList(this, items)
            val listView: ListView = findViewById(R.id.DiseaselistView)
            listView.adapter = adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = items[position]
                val sharedPreferences = getSharedPreferences("DoctorPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("doctorName", selectedItem.doctorName)
                editor.putString("doctorBio", selectedItem.doctorBio)
                editor.putString("doctorProfile", selectedItem.imageUrl)
                editor.apply()

                val intent = Intent(this@DoctorListActivity, DoctorDetailsActivity::class.java)
                startActivity(intent)
                //Toast.makeText(this, selectedItem.doctorName, Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
    }
}