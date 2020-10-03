package com.example.wp.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager, val pages: List<Fragment>, val titles:List<String>) :
    FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}