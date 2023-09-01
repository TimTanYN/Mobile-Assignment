package com.example.tracking

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import com.example.tracking.model.ListViewMedicine

class MedicineActivity : BaseNavigationActivity(){

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Medicine"
        initToolbarAndNavigation()



        val items = listOf(
            ListViewMedicine.ListItem(R.drawable.big, "Big Pharmacy", "TextView1"),
            ListViewMedicine.ListItem(R.drawable.big, "Another Pharmacy", "TextView2")
            // ... add more items as needed
        )

        val adapter = com.example.tracking.adapter.ListViewMedicine(this, items)
        val listView: ListView = findViewById(R.id.list_view)
        listView.adapter = adapter

        val searchEditText: EditText = findViewById(R.id.search_input)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)

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

    }


    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
