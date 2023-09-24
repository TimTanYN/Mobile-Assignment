package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.FoodPyramidFragment
import com.example.tracking.VeganFragment

internal class FoodPyramidAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FoodPyramidFragment()
            1 -> VeganFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }


}