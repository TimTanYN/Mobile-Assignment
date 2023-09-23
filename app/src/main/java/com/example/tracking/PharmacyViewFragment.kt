package com.example.tracking

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import com.example.tracking.model.ListViewMedicine
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PharmacyViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PharmacyViewFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_pharmacy_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateListView()


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PharmacyViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PharmacyViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private lateinit var listView: ListView

    fun updateListView() {
        val view = view ?: return
        listView = view.findViewById(R.id.list_view)
        val db = FirebaseFirestore.getInstance()
        val pharmacyCollection = db.collection("/medicine")
        pharmacyCollection.get().addOnSuccessListener { querySnapshot ->
            val items = querySnapshot.documents.map { document ->
                val pharmacyName = document.getString("pharmacyName") ?: ""
                val pharmacyDesc = document.getString("pharmacyDesc") ?: ""
                val imageUrl = document.getString("URL") ?: ""
                ListViewMedicine.ListItem(imageUrl, pharmacyName, pharmacyDesc)
            }
            val adapter = com.example.tracking.adapter.ListViewMedicine(requireContext(), items)

            listView.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
    }
}