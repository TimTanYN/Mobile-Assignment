package com.example.tracking

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
 * Use the [UpdateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFragment : Fragment() {
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

    var selectedItem: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_update, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayOf("Kuala Lumpur", "Subang Jaya", "PutraJaya")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = items[position]



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where no item is selected, if necessary
            }
        }

        val button = view?.findViewById<Button>(R.id.submit)
        button?.setOnClickListener {
            update()
        }
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun update() {
        val db = FirebaseFirestore.getInstance()
        val view = view ?: return  // Ensure the fragment's view is not null


        val currentDate = view.findViewById<EditText>(R.id.date).text.toString()
        val covid = Integer.parseInt(view.findViewById<EditText>(R.id.Covid19).text.toString())
        val coli = Integer.parseInt(view.findViewById<EditText>(R.id.Ecoli).text.toString())
        val flu = Integer.parseInt(view.findViewById<EditText>(R.id.flu).text.toString())
        val documentReference = db.collection("disease")
            .document(selectedItem.toString())
            .collection(currentDate)
            .document("disease")

        // Create a map of fields to update
        val updates: MutableMap<String, Any> = hashMapOf(
            "Covid-19" to covid as Any,
            "E-Coli" to coli as Any,
            "Flu" to flu as Any
            // ... add more fields as needed
        )

        // Update the fields
        documentReference.update(updates)
            .addOnSuccessListener {
                Log.d(TAG, "Document successfully updated!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error updating document", e)
            }
    }


}