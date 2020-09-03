package com.example.wp.presentation.menuscontainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.R
import com.example.wp.presentation.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_base_input_menu.*
import kotlinx.android.synthetic.main.fragment_menus_container.*

class MenusContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menus_container, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewpager_main.adapter = TabAdapter(childFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)

    }

}