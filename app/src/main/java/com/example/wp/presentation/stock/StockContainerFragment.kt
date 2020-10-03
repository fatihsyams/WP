package com.example.wp.presentation.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.adapter.TabAdapter
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.stock.checkstock.CheckStockFragment
import kotlinx.android.synthetic.main.fragment_menus_container.*

class StockContainerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menus_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createStockFragment = CreateStockFragment()
        val checkStockFragment = CheckStockFragment()

        viewpager_main.adapter =
            TabAdapter(childFragmentManager, listOf(createStockFragment, checkStockFragment),
            listOf("Tambah Stok", "Cek Stok"))
        tabs_main.setupWithViewPager(viewpager_main)

    }
}