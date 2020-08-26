package com.example.wp.presentation.order

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_TYPE
import com.example.wp.presentation.listener.OpenMenuPageListener
import com.example.wp.utils.AppConstants
import com.example.wp.utils.gone
import com.example.wp.utils.showToast
import com.example.wp.utils.visible
import kotlinx.android.synthetic.main.fragment_order.*
import java.util.ArrayList

class OrderFragment : WarungPojokFragment() {

    companion object {
        @JvmStatic
        fun newInstance(menus:List<DataItem>) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(AppConstants.KEY_MENU, menus as ArrayList<DataItem>)
                }
            }
    }

    private var menus = listOf<DataItem>()

    var onAddMenuListener:OpenMenuPageListener? = null

    override val layoutView: Int = R.layout.fragment_order

            override
    fun onPreparation() {
    }

    override fun onIntent() {
        menus = arguments?.getParcelableArrayList<DataItem>(AppConstants.KEY_MENU).orEmpty()
    }

    override fun onView() {
        showMenus()
    }

    override fun onAction() {
        btnDineIn.setOnClickListener {
            dineInContainer.visible()
            orderTypeContainer.gone()
        }

        btnTakeAway.setOnClickListener {
            takeAwayContainer.visible()
            orderTypeContainer.gone()
        }

        btnPreOrder.setOnClickListener {
            preOrderContainer.visible()
            orderTypeContainer.gone()
        }

        btnAdd.setOnClickListener {
            onAddMenuListener?.onOpenMenuPage()
        }

        btnPrint.setOnClickListener { showToast("print") }
    }

    override fun onObserver() {
    }

    private fun showMenus(){

        val menuAdapter = MenusAdapter(
            context = requireContext(),
            data = menus,
            type = ORDER_TYPE
        )

        rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }

    }

}