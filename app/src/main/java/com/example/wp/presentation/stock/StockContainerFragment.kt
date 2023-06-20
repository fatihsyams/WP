package com.example.wp.presentation.stock

import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.presentation.adapter.TabAdapter
import com.example.wp.presentation.listener.StockListener
import com.example.wp.presentation.stock.checkstock.CheckStockFragment
import kotlinx.android.synthetic.main.fragment_menus_container.*

class StockContainerFragment : WarungPojokFragment(), CreateStockFragment.OnStockListener {

    var onStockListener:StockListener? = null

    override val layoutView: Int = R.layout.fragment_menus_container

    override fun onPreparation() {
    }

    override fun onIntent() {
    }

    override fun onView() {
        val createStockFragment = CreateStockFragment()
        createStockFragment.onStockListener = this

        val checkStockFragment = CheckStockFragment()

        viewpager_main.adapter =
            TabAdapter(
                childFragmentManager, listOf(createStockFragment, checkStockFragment),
                listOf("Tambah Stok", "Cek Stok")
            )
        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onAction() {
    }

    override fun onObserver() {
    }

    override fun onSubmitStock() {
        viewpager_main.currentItem = 1
        onStockListener?.onStockCreated()
    }
}