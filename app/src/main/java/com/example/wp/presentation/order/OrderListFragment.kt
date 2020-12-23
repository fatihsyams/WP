package com.example.wp.presentation.order

import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.presentation.adapter.OrderResultAdapter
import kotlinx.android.synthetic.main.fragment_order_list.*

class OrderListFragment : WarungPojokFragment() {

    override val layoutView: Int = R.layout.fragment_order_list

    override fun onPreparation() {
    }

    override fun onIntent() {
    }

    override fun onView() {
        showOrders()
    }

    override fun onAction() {
    }

    override fun onObserver() {
    }

    private fun showOrders() {
        val datas = listOf(
            OrderResult(
                order = Order(
                    customerName = "fatih",
                    orderCategory = "dine in"
                )
            ), OrderResult(
                order = Order(
                    customerName = "fatih",
                    orderCategory = "dine in"
                )
            ), OrderResult(
                order = Order(
                    customerName = "fatih",
                    orderCategory = "dine in"
                )
            )
        )

        val orderAdapter = OrderResultAdapter(requireContext(), datas)
        rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }
}