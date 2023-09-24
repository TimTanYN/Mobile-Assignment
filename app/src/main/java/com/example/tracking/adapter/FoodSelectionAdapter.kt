package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.CarbsFragment
import com.example.tracking.DrinksFragment
import com.example.tracking.MeatSelectionFragment
import com.example.tracking.VegFragment

internal class FoodSelectionAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            //pending activity implementation
            0 -> MeatSelectionFragment()
            1 -> VegFragment()
            2 -> CarbsFragment()
            3 -> DrinksFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }


}