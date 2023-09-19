package com.example.tracking

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicine_details)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Medicine"
        initToolbarAndNavigation()
        listview = findViewById(R.id.medicine)
        list()
        Create()

    }

    val cartItems = mutableListOf<ListViewMedicineDetails.CartItem>()

    fun list(){
        val db = FirebaseFirestore.getInstance()
        val cart = mutableListOf<ListViewMedicineDetails.ListItems>()
        var products =  mutableListOf<ListViewMedicineDetails.ListItems>()
        db.collection("AA").document("product")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Extracting field names from the document
                    val fetchedProducts = documentSnapshot.data?.keys?.map {
                        ListViewMedicineDetails.ListItems(R.drawable.big, it, 10.0)
                    } ?: listOf()

                    // Add fetched products to the products list
                    products.addAll(fetchedProducts)

                    val adapter = com.example.tracking.adapter.ListViewMedicineDetails(this, products)
                    listview.adapter = adapter
                }
            }

        listview.setOnItemClickListener { _, _, position, _ ->
            val selectedProduct = products[position]
            //            cart.add(selectedProduct)
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

    }


