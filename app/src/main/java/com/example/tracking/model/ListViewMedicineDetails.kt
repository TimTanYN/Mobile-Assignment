package com.example.tracking.model

class ListViewMedicineDetails {
    data class ListItems(val imageResId: Int, val medicineName: String, val price: Double)
    data class CartItem(val product: ListItems, var quantity: Int)
}