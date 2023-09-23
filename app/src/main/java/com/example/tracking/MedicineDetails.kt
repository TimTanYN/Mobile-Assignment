package com.example.tracking

import android.app.AlertDialog
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
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.tracking.adapter.ListViewAdapter
import com.example.tracking.model.ListViewMedicineDetails
import com.example.tracking.model.ListViewModel
import com.google.firebase.firestore.FirebaseFirestore

class MedicineDetails : BaseNavigationActivity() {
    private lateinit var listview: ListView
    var pharmacy = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Medicine"
        initToolbarAndNavigation()
        pharmacy = intent.getStringExtra("selected").toString()
        listview = findViewById(R.id.medicine)
        list()
        Create()

        val searchEditText: EditText = findViewById(R.id.search_medicine)
        listview = findViewById(R.id.medicine)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (listview.adapter as com.example.tracking.adapter.ListViewMedicineDetails).filter.filter(s.toString())
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

    val cartItems = mutableListOf<ListViewMedicineDetails.CartItem>()

    fun list(){
        val db = FirebaseFirestore.getInstance()
        val cart = mutableListOf<ListViewMedicineDetails.ListItems>()
        var products =  mutableListOf<ListViewMedicineDetails.ListItems>()
        db.collection(pharmacy)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    if (document != null && document.exists()) {
                        // Extracting field names from the document
                        val fetchedProducts = document.data?.keys?.map {
                            ListViewMedicineDetails.ListItems(R.drawable.big, it, 10.0)
                        } ?: listOf()

                        // Add fetched products to the products list
                        products.addAll(fetchedProducts)
                    }
                }

                val adapter = com.example.tracking.adapter.ListViewMedicineDetails(this, products)
                listview.adapter = adapter
            }

        listview.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = products[position]
            //            cart.add(selectedProduct)
            println(selectedProduct)
            CartManager.addToCart(selectedProduct, 1)
            Toast.makeText(this, "${selectedProduct.medicineName} added to cart!", Toast.LENGTH_SHORT).show()
        }



    }

    object CartManager {
        val cartItems = mutableListOf<ListViewMedicineDetails.CartItem>()

        fun addToCart(product: ListViewMedicineDetails.ListItems, quantity: Int) {
            val existingItem = cartItems.find { it.product.medicineName == product.medicineName }
            if (existingItem != null) {
                existingItem.quantity += quantity
            } else {
                cartItems.add(ListViewMedicineDetails.CartItem(product, quantity))
            }
            println("Cart items count: ${CartManager.cartItems.size}")
            for (item in cartItems) {
                Log.d("MyApp", "Item: ${item.product.medicineName}, Quantity: ${item.quantity}")
            }

        }

        fun getTotalPrice(): Double {
            return cartItems.sumByDouble { it.product.price * it.quantity }
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    }


