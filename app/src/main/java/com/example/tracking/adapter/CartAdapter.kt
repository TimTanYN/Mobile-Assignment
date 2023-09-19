package com.example.tracking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tracking.R
import com.example.tracking.model.ListViewMedicineDetails

class CartAdapter(private val items: MutableList<ListViewMedicineDetails.CartItem>, private val onItemRemoved: (ListViewMedicineDetails.CartItem) -> Unit, private val onQuantityChanged: (ListViewMedicineDetails.CartItem, Int) -> Unit) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ListViewMedicineDetails.CartItem) {
            view.findViewById<TextView>(R.id.productName).text = item.product.medicineName
            val quantityTextView = view.findViewById<TextView>(R.id.quantity)
            quantityTextView.text = item.quantity.toString()

            view.findViewById<Button>(R.id.removeButton).setOnClickListener {
                onItemRemoved(item)
            }

            view.findViewById<Button>(R.id.increaseQuantityButton).setOnClickListener {
                item.quantity++
                onQuantityChanged(item, item.quantity)
                quantityTextView.text = item.quantity.toString()
            }

            view.findViewById<Button>(R.id.decreaseQuantityButton).setOnClickListener {
                if (item.quantity > 1) {
                    item.quantity--
                    onQuantityChanged(item, item.quantity)
                    quantityTextView.text = item.quantity.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_medicine, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun getCartItems(): List<ListViewMedicineDetails.CartItem> {
        return items
    }
}