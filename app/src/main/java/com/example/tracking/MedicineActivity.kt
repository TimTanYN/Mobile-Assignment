package com.example.tracking

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import com.example.tracking.model.ListViewMedicine
import com.example.tracking.model.ListViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MedicineActivity : BaseNavigationActivity(){

    private lateinit var listView: ListView
    lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Medicine"
        initToolbarAndNavigation()
        updateListView()
        val searchEditText: EditText = findViewById(R.id.search_input)
        listView = findViewById(R.id.list_view)
        Create()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (listView.adapter as com.example.tracking.adapter.ListViewMedicine).filter.filter(s.toString())
            }
        })
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(searchEditText)
                true
            } else {
                false
            }
        }
        getListItem()
        Create()

    }


    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun updateListView() {
        val db = FirebaseFirestore.getInstance()
        val pharmacyCollection = db.collection("/medicine")
        pharmacyCollection.get().addOnSuccessListener { querySnapshot ->
            val items = querySnapshot.documents.map { document ->
                val pharmacyName = document.getString("pharmacyName") ?: ""
                val pharmacyDesc = document.getString("pharmacyDesc") ?: ""
                val imageUrl = document.getString("URL") ?: ""
                ListViewMedicine.ListItem(imageUrl, pharmacyName, pharmacyDesc)
            }
            val adapter = com.example.tracking.adapter.ListViewMedicine(this, items)

            listView.adapter = adapter
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }
    }

    fun getListItem(){
        listView.setOnItemClickListener { parent, view, position, id ->

            val selectedItem = listView.adapter.getItem(position) as ListViewMedicine.ListItem
            Toast.makeText(this, "Selected: ${selectedItem.pharmacyName}", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MedicineDetails::class.java)
            intent.putExtra("selected",selectedItem.pharmacyName)
            startActivity(intent)
        }
    }
}
