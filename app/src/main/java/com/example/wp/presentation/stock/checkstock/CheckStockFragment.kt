package com.example.wp.presentation.stock.checkstock

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.R
import com.example.wp.domain.menu.Menu

import com.example.wp.presentation.adapter.CheckStockAdapter
import com.example.wp.presentation.listener.StockListener
import kotlinx.android.synthetic.main.fragment_check_stock.*


class CheckStockFragment : Fragment(), CheckStockInterface.View, StockListener {

    lateinit var presenter: CheckStockInterface.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_stock, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CheckStockPresenter(this)
        context?.let {
            presenter.instencePrefence(it)
        }
        presenter.getDataStock()


    }

    override fun showData(data: List<Menu>) {
        rvCheckStok.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CheckStockAdapter(context, data, this@CheckStockFragment)
        }
    }

    override fun showEror(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        presenter.getDataStock()
    }

    override fun onSeveClicked(menu: Menu) {
        presenter.updateStock(menu.id, menu.quantity)

    }


}