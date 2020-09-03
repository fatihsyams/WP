package com.example.wp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.wp.presentation.checkmenu.CheckMenuFragment
import com.example.wp.presentation.createmenu.CreateMenuFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {


    private val pages = listOf(
        CreateMenuFragment(),
        CheckMenuFragment()
    )

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Create Menu"
            else -> "Check Menu"
        }
    }

}