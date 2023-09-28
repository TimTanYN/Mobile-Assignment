package com.example.tracking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class SurveyUpdate : AppCompatActivity() {

    // Define UI elements
    private lateinit var surveyID: EditText
    private lateinit var updateQues1: EditText
    private lateinit var updateQues2: EditText
    private lateinit var updateQues3: EditText
    private lateinit var updateQues4: EditText
    private lateinit var btnUpdateSurvey: Button
    private lateinit var btnDisplay: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_update)

        FirebaseApp.initializeApp(this)

        // Initialize UI elements
        surveyID = findViewById(R.id.surveyID)
        updateQues1 = findViewById(R.id.updateQues1)
        updateQues2 = findViewById(R.id.updateQues2)
        updateQues3 = findViewById(R.id.updateQues3)
        updateQues4 = findViewById(R.id.updateQues4)
        btnUpdateSurvey = findViewById(R.id.btnSurveyUpdate)
        btnDisplay = findViewById(R.id.btnDisplay)

        // Implement your update logic when the "Update" button is clicked
        btnDisplay.setOnClickListener {
            val surveyIdInput = surveyID.text.toString() // Get the inputted survey document name

            // Check if the survey ID input is not empty
            if (surveyIdInput.isNotEmpty()) {
                val docRef =
                    FirebaseFirestore.getInstance().collection("survey").document(surveyIdInput)

                docRef.get()
                    .addOnSuccessListener { documentSnapshot ->
                        if (documentSnapshot.exists()) {
                            // Survey document with the specified ID exists
                            val surveyData = documentSnapshot.data as Map<String, Any>?

                            // Retrieve the values from the document data and set them in EditText fields
                            val question1 = surveyData?.get("Question1") as? String ?: ""
                            val question2 = surveyData?.get("Question2") as? String ?: ""
                            val question3 = surveyData?.get("Question3") as? String ?: ""
                            val question4 = surveyData?.get("Question4") as? String ?: ""

                            updateQues1.setText(question1)
                            updateQues2.setText(question2)
                            updateQues3.setText(question3)
                            updateQues4.setText(question4)
                        } else {
                            // Survey document with the specified ID does not exist
                            Toast.makeText(this, "Survey not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error fetching document: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(this, "Please enter a survey ID", Toast.LENGTH_SHORT).show()
            }
        }

        btnUpdateSurvey.setOnClickListener {
            val surveyIdInput = surveyID.text.toString() // Get the inputted survey document name

            // Check if the survey ID input is not empty
            if (surveyIdInput.isNotEmpty()) {
                val docRef =
                    FirebaseFirestore.getInstance().collection("survey").document(surveyIdInput)

                val updatedData = hashMapOf<String, Any>()

                // Check each field and add it to the update data if it's not empty
                val question1Value = updateQues1.text.toString()
                if (question1Value.isNotEmpty()) {
                    updatedData["Question1"] = question1Value
                }

                val question2Value = updateQues2.text.toString()
                if (question2Value.isNotEmpty()) {
                    updatedData["Question2"] = question2Value
                }

                val question3Value = updateQues3.text.toString()
                if (question3Value.isNotEmpty()) {
                    updatedData["Question3"] = question3Value
                }

                val question4Value = updateQues4.text.toString()
                if (question4Value.isNotEmpty()) {
                    updatedData["Question4"] = question4Value
                }

                // Update the values in Firestore or perform any other necessary actions
                // You can use Firestore here to update the values
                docRef
                    .update(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Survey updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            this,
                            "Error updating document: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            } else {
                Toast.makeText(this, "Please enter a survey ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
}