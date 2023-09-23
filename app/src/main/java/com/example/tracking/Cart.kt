package com.example.tracking

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.adapter.CartAdapter
import com.example.tracking.model.ListViewMedicineDetails

class Cart: BaseNavigationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Tracking"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        cart()
       onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val proceedToCheckoutButton: Button = findViewById(R.id.proceedToCheckoutButton)

        proceedToCheckoutButton.setOnClickListener {
            val cartItems = (recyclerView.adapter as CartAdapter).getCartItems()
            if (cartItems.isNotEmpty()) {
                val totalPrice = MedicineDetails.CartManager.getTotalPrice()

                Toast.makeText(this, "Total Price: $${String.format("%.2f", totalPrice)}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show()
            }
        }



    }

    fun cart(){
        val cartItems = MedicineDetails.CartManager.cartItems
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = CartAdapter(cartItems,
            onItemRemoved = { item ->
                cartItems.remove(item)
                recyclerView.adapter?.notifyDataSetChanged()
            },
            onQuantityChanged = { item, newQuantity ->
                // Handle quantity change if needed
            })
    }

    override fun onResume() {
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
        cart()
    }

}