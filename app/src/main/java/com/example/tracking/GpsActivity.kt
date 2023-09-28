package com.example.tracking

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tracking.databinding.GpsriskBinding

class GpsActivity:AppCompatActivity(){

    private var startLoc = ""
    private var endLoc = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: GpsriskBinding = DataBindingUtil.setContentView(this, R.layout.gpsrisk)

        class MainViewModel {
            val text = "Hello, Data Binding!"
        }

        val result = binding.btnResult


        FirebaseApp.initializeApp(this)

        result.setOnClickListener(){
            startLoc = binding.startLoc.text.toString()
            endLoc = binding.endLoc.text.toString()
            displayResult()
        }

    }



    fun displayResult(){

        var risk = 0
        val startLoc = startLoc
        val endLoc = endLoc
        val riskMessage = findViewById<TextView>(R.id.riskMessage)
        val preventMessage = findViewById<TextView>(R.id.preventMessage)

        if (startLoc == "Maxim" || endLoc == "Maxim"){
            risk += 30
        }

        if (startLoc == "TARUMT" || endLoc == "TARUMT"){
            risk += 5
        }

        if (startLoc == "PV16" || endLoc == "PV16"){
            risk += 60
        }

        if (startLoc == "Teratai" || endLoc == "Teratai"){
            risk += 10
        }

        if (startLoc.toString().trim().isEmpty() || endLoc.toString().trim().isEmpty())
        {
            Toast.makeText(this, "Both location must be written", Toast.LENGTH_SHORT).show();
        } else if((startLoc == "Maxim" || startLoc == "TARUMT" || startLoc == "PV16" || startLoc == "Teratai") && (endLoc == "Maxim" || endLoc == "TARUMT" || endLoc == "PV16" || endLoc == "Teratai")){
            if (risk >= 0 && risk <= 29) {
                riskMessage.text = "No risk in this area"
                preventMessage.text = "No prevention suggested"
            } else if (risk >= 30 && risk <= 59) {
                riskMessage.text = "May infected to Covid-19 since this area has high covid case"
                preventMessage.text = "Please wear a face mask when on the destination"
            } else if (risk >= 60 && risk <= 100) {
                riskMessage.text = "May infected to dengue fever since this area has many similar cases "
                preventMessage.text = "Please wear long-sleeved shirts and long when on the destination"
            }

            val db = FirebaseFirestore.getInstance()

            val counterRef = db.collection("location").document("Location")

            db.runTransaction { transaction ->
                // Get the current counter value
                val counterSnapshot = transaction.get(counterRef)
                val currentCounter = counterSnapshot.getLong("Location") ?: 999 // Assuming 999 as default if not set

                // Increment the counter
                val newCounter = currentCounter + 1

                // Create a new map of data
                val data = hashMapOf(
                    "startLocation" to startLoc,
                    "endLocation" to endLoc,
                    "riskMessage" to riskMessage.text,
                    "preventMessage" to preventMessage.text,
                )

                // Reference to the new survey document
                val newLocationRef = db.collection("location").document("Location$newCounter")

                // Set the survey data to the new document
                transaction.set(newLocationRef, data)

                // Update the counter document with the new value
                transaction.update(counterRef, "Location", newCounter)
            }
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "Transaction success!")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Transaction failure.", e)
                }
        }else
        {
            Toast.makeText(this, "Please enter location keywords", Toast.LENGTH_SHORT).show();
        }
    }
}