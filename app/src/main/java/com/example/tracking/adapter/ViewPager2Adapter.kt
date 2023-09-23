package com.example.tracking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.tracking.DeleteFragment
import com.example.tracking.CreateFragment
import com.example.tracking.ViewFragment
import com.example.tracking.UpdateFragment


class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 4

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ViewFragment()
                1 -> CreateFragment()
                2 -> UpdateFragment()
                3 -> DeleteFragment()
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }
    }
