package com.example.tracking

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab1Fragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create, container, false)

        val button = view.findViewById<Button>(R.id.submit)
        button.setOnClickListener {
            add()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab1Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    val db = FirebaseFirestore.getInstance()

    fun add(){
        println("hi")
        val view = view ?: return  // Ensure the fragment's view is not null

        val city = view.findViewById<EditText>(R.id.city).text.toString()
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val covid = Integer.parseInt(view.findViewById<EditText>(R.id.Covid19).text.toString())
        val coli = Integer.parseInt(view.findViewById<EditText>(R.id.Ecoli).text.toString())
        val flu = Integer.parseInt(view.findViewById<EditText>(R.id.flu).text.toString())
        val data: MutableMap<String, Any> = HashMap()
        data["Covid-19"] = covid
        data["E.Coli"] = coli
        data["Flu"] = flu

        val newDocRef = db.collection("disease").document(city).collection(currentDate).document("disease")
        newDocRef.set(data)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Document successfully written!")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error writing document", e)
            }
    }




}