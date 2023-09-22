package com.example.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import com.example.tracking.model.ListViewBackendDisease
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tab2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tab2Fragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_tab2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = arrayOf("Kuala Lumpur", "Subang Jaya", "PutraJaya")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        val spinner: Spinner = view.findViewById(R.id.spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedItem = items[position]
                listview()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle case where no item is selected, if necessary
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tab2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tab2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun listview(){
        val view = view ?: return  // Ensure the fragment's view is not null
        val listView = view.findViewById<ListView>(R.id.viewList)
        val db = FirebaseFirestore.getInstance()
        val kualaLumpurRef = db.collection("disease").document(selectedItem.toString())
        // Generate a list of the last 30 days
        val dateList = generateLastNDates(30)

        val items = mutableListOf<ListViewBackendDisease.ListItem>()

        for (date in dateList) {
            println(date)
            kualaLumpurRef.collection(date).document("disease").get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val data = document.data
                    val covid = data?.get("Covid-19").toString()
                    val coli = data?.get("E.Coli").toString()
                    val flu = data?.get("Flu").toString()
                    val listItem =
                        ListViewBackendDisease.ListItem("Kuala Lumpur", date, covid, coli, flu)
                    items.add(listItem)

                    // Update the ListView
                    val adapter = com.example.tracking.adapter.ListViewBackendDisease(requireContext(), items)
                    listView.adapter = adapter
                }
            }
        }

    }

    fun generateLastNDates(n: Int): List<String> {
        val dateList = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        for (i in 0 until n) {
            dateList.add(dateFormat.format(calendar.time))
            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        return dateList
    }




}