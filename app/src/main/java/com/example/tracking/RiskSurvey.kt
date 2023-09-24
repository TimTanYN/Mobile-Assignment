package com.example.tracking

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class RiskSurvey: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.risk_survey)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        supportActionBar?.title = "Tracking"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        FirebaseApp.initializeApp(this)

//        databaseReference = FirebaseDatabase.getInstance().reference

        val cont = findViewById<Button>(R.id.btnCont)

        cont.setOnClickListener(){
            saveResult()
            displayRisk()
        }
    }

    fun saveResult() {
        val ques1 = findViewById<RadioGroup>(R.id.ques1btnGroup)
        val ques2 = findViewById<RadioGroup>(R.id.ques2btnGroup)
        val ques3 = findViewById<RadioGroup>(R.id.ques3btnGroup)
        val ques4 = findViewById<RadioGroup>(R.id.ques4btnGroup)

        val selectedIdQues1 = ques1.checkedRadioButtonId
        val selectedIdQues2 = ques2.checkedRadioButtonId
        val selectedIdQues3 = ques3.checkedRadioButtonId
        val selectedIdQues4 = ques4.checkedRadioButtonId

        val radioButtonQues1 = findViewById<RadioButton>(selectedIdQues1)
        val radioButtonQues2 = findViewById<RadioButton>(selectedIdQues2)
        val radioButtonQues3 = findViewById<RadioButton>(selectedIdQues3)
        val radioButtonQues4 = findViewById<RadioButton>(selectedIdQues4)

        val selectedValueQues1 = radioButtonQues1.text.toString()
        val selectedValueQues2 = radioButtonQues2.text.toString()
        val selectedValueQues3 = radioButtonQues3.text.toString()
        val selectedValueQues4 = radioButtonQues4.text.toString()

        val db = FirebaseFirestore.getInstance()

        val counterRef = db.collection("survey").document("Survey")

        // Run a transaction
        db.runTransaction { transaction ->
            // Get the current counter value
            val counterSnapshot = transaction.get(counterRef)
            val currentCounter = counterSnapshot.getLong("Survey") ?: 999 // Assuming 999 as default if not set

            // Increment the counter
            val newCounter = currentCounter + 1

            // Create a new map of data
            val data = hashMapOf(
                "Question1" to selectedValueQues1,
                "Question2" to selectedValueQues2,
                "Question3" to selectedValueQues3,
                "Question4" to selectedValueQues4
            )

            // Reference to the new survey document
            val newSurveyRef = db.collection("survey").document("Survey$newCounter")

            // Set the survey data to the new document
            transaction.set(newSurveyRef, data)

            // Update the counter document with the new value
            transaction.update(counterRef, "Survey", newCounter)
        }
            .addOnSuccessListener {
                Log.d(TAG, "Transaction success!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Transaction failure.", e)
            }
    }

    fun displayRisk(){
        val ques1 = findViewById<RadioGroup>(R.id.ques1btnGroup)
        val ques2 = findViewById<RadioGroup>(R.id.ques2btnGroup)
        val ques3 = findViewById<RadioGroup>(R.id.ques3btnGroup)
        val ques4 = findViewById<RadioGroup>(R.id.ques4btnGroup)

        val selectedIdQues1 = ques1.checkedRadioButtonId
        val selectedIdQues2 = ques2.checkedRadioButtonId
        val selectedIdQues3 = ques3.checkedRadioButtonId
        val selectedIdQues4 = ques4.checkedRadioButtonId

        val radioButtonQues1 = findViewById<RadioButton>(selectedIdQues1)
        val radioButtonQues2 = findViewById<RadioButton>(selectedIdQues2)
        val radioButtonQues3 = findViewById<RadioButton>(selectedIdQues3)
        val radioButtonQues4 = findViewById<RadioButton>(selectedIdQues4)

        val ques1Selected = radioButtonQues1.text.toString()
        val ques2Selected = radioButtonQues2.text.toString()
        val ques3Selected = radioButtonQues3.text.toString()
        val ques4Selected = radioButtonQues4.text.toString()

        var risk =  0
        var isHighRisk = false
        var isModerateRisk = false
        var isLowRisk = false

        if(ques1Selected == "Yes"){
            risk +=25
        }

        if(ques2Selected == "Rarely" || ques2Selected == "Never"){
            risk +=25
        }

        if(ques3Selected == "Rarely" || ques2Selected == "Never"){
            risk +=25
        }

        if(ques4Selected == "Yes"){
            risk +=25
        }

        if(risk >= 0 && risk <= 25){
            Toast.makeText(this, "You are detected as low-risk", Toast.LENGTH_SHORT).show()
        }else if(risk == 50){
            Toast.makeText(this, "You are detected as Moderate-risk", Toast.LENGTH_SHORT).show()
        }else if(risk >= 75 && risk <= 100){
            Toast.makeText(this, "You are detected as high-risk", Toast.LENGTH_SHORT).show()
        }

//        if ((ques1Selected == "Yes" && (ques2Selected == "Rarely" || ques2Selected == "Never")
//            && (ques3Selected == "Rarely" || ques3Selected == "Never") && (ques4Selected == "Yes" || ques4Selected == "No")) ||
//                (ques1Selected == "No" || ques1Selected == "Yes") && (ques2Selected == "Rarely" || ques2Selected == "Never")
//                && (ques3Selected == "Rarely" || ques3Selected == "Never") && (ques4Selected == "Yes" )) {
//            isHighRisk = true
//        }
//        else if ((ques1Selected == "Yes" && (ques2Selected == "Often" || ques2Selected == "Sometimes")
//            && (ques3Selected == "Annually" || ques3Selected == "Biannually") && (ques4Selected == "Yes" || ques4Selected == "No")) ||
//            (ques1Selected == "No" || ques1Selected == "Yes") && (ques2Selected == "Often" || ques2Selected == "Sometimes")
//                    && (ques3Selected == "Annually" || ques3Selected == "Biannually") && (ques4Selected == "Yes")){
//            isModerateRisk = true
//        }
//        else {
//            isLowRisk = true
//        }

//        if (isHighRisk) {
//            Toast.makeText(this, "You are detected as high-risk", Toast.LENGTH_SHORT).show()
//        }
//        else if (isModerateRisk) {
//            Toast.makeText(this, "You are detected as Moderate-risk", Toast.LENGTH_SHORT).show()
//        }
//        else if (isLowRisk) {
//            Toast.makeText(this, "You are detected as low-risk", Toast.LENGTH_SHORT).show()
//        }
    }
}