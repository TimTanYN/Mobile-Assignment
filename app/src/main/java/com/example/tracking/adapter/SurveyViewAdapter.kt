package com.example.tracking.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.R
import com.example.tracking.SurveyUpdate
import com.example.tracking.model.ListViewDoctorList
import com.example.tracking.model.ListViewSurvey
import com.google.firebase.firestore.FirebaseFirestore


class SurveyViewAdapter(context: Context, data: List<ListViewSurvey.SurveyListItem>):
    ArrayAdapter<ListViewSurvey.SurveyListItem>(context, 0, data) {

    private var selectedItemPosition: Int = -1
    private val db = FirebaseFirestore.getInstance()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentItem = getItem(position) as ListViewSurvey.SurveyListItem
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_survey, parent, false)

        // Populate the data into the views here
        val surveyText: TextView = view.findViewById(R.id.surveyText)
        val btnSurveyDel: Button = view.findViewById(R.id.btnSurveyDel)

        // Set the text for TextViews based on the SurveyListItem object
        surveyText.text = "Question 1: ${currentItem.Question1}\n" +
                "Question 2: ${currentItem.Question2}\n" +
                "Question 3: ${currentItem.Question3}\n" +
                "Question 4: ${currentItem.Question4}"

        surveyText.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged() // Notify the adapter to refresh the view

            // Optionally, you can display a message to indicate the selected item
            Toast.makeText(context, "Selected survey: ${position}", Toast.LENGTH_SHORT).show()
        }

        btnSurveyDel.setOnClickListener{
            val documentId = "Survey${position + 1}"
            deleteSurvey(position, documentId)
        }

        return view
    }

    fun deleteSurvey(position: Int, documentId: String) {
        // Delete the document from Firestore
        db.collection("survey")
            .document(documentId)
            .delete()
            .addOnSuccessListener {
                // Document successfully deleted
                Toast.makeText(context, "Survey deleted successfully.", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e ->
                // Handle errors if deletion fails
                Toast.makeText(context, "Error deleting survey: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}