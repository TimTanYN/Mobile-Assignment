package com.example.tracking

import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart)
        sharedPreferences = getSharedPreferences("Cart", MODE_PRIVATE)

        cart()
       onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val proceedToCheckoutButton: Button = findViewById(R.id.proceedToCheckoutButton)

        proceedToCheckoutButton.setOnClickListener {
            val cartItems = (recyclerView.adapter as CartAdapter).getCartItems()
            if (cartItems.isNotEmpty()) {
                val totalPrice = MedicineDetails.CartManager.getTotalPrice()
                val editor = sharedPreferences.edit()
                editor.putLong("totalPrice", totalPrice.toLong())
                editor.apply()
                val intent = Intent(this, BuyerDetails::class.java)
                startActivity(intent)
                Toast.makeText(this, "Total Price: $${String.format("%.2f", totalPrice)}", Toast.LENGTH_SHORT).show()
                clearCart()
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

    fun clearCart() {
        val cartItems = MedicineDetails.CartManager.cartItems
        cartItems.clear()
        val recyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        recyclerView.adapter?.notifyDataSetChanged()
    }

}