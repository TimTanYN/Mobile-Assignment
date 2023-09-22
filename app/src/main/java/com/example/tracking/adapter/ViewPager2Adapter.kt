package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.Tab1Fragment
import com.example.tracking.Tab2Fragment
import com.example.tracking.UpdateFragment


class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> Tab1Fragment()
                1 -> Tab2Fragment()
                2 -> UpdateFragment()
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }
    }
