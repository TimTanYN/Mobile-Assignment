package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.NormalPlanFragment
import com.example.tracking.VeganPlanFragment

internal class DietPlanAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NormalPlanFragment()
            1 -> VeganPlanFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }


}