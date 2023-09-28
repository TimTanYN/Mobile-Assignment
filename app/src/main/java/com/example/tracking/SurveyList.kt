package com.example.tracking

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tracking.R
import com.example.tracking.adapter.SurveyViewAdapter
import com.example.tracking.model.ListViewBackendDisease
import com.example.tracking.model.ListViewSurvey
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class SurveyList : AppCompatActivity() {

    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_list)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Call a method to fetch and display data from Firestore
        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val surveyCollection = db.collection("survey")

        // Define and populate the list of excluded document IDs
        val excludedDocumentIds = mutableListOf<String>()
        excludedDocumentIds.add("Survey") // Add the document ID you want to exclude

        // Query documents in the "survey" collection excluding specific document IDs
        surveyCollection.whereNotIn(FieldPath.documentId(), excludedDocumentIds)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val surveyDataList = mutableListOf<ListViewSurvey.SurveyListItem>()

                for (document in querySnapshot.documents) {
                    val data = document.toObject(ListViewSurvey.SurveyListItem::class.java)
                    if (data != null) {
                        val ques1 = data.Question1
                        val ques2 = data.Question2
                        val ques3 = data.Question3
                        val ques4 = data.Question4
                        val surveyListItem = ListViewSurvey.SurveyListItem(ques1, ques2, ques3, ques4)
                        surveyDataList.add(surveyListItem)
                    }
                }

                if (surveyDataList.isNotEmpty()) {
                    // Display the survey data using your adapter
                    val surveyListView = findViewById<ListView>(R.id.surveyListView)
                    val adapter = SurveyViewAdapter(this, surveyDataList)
                    surveyListView.adapter = adapter
                } else {
                    // Handle the case when there is no survey data
                    Toast.makeText(this, "No survey data available.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error fetching documents: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}