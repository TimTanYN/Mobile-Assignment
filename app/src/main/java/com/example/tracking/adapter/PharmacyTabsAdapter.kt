package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.CreateFragment
import com.example.tracking.DeleteFragment
import com.example.tracking.PharmacyCreateFragment
import com.example.tracking.PharmacyViewFragment
import com.example.tracking.UpdateFragment
import com.example.tracking.ViewFragment

class PharmacyTabsAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PharmacyViewFragment()
            1 -> PharmacyCreateFragment()
            2 -> UpdateFragment()
            3 -> DeleteFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
}