package com.example.wp.presentation.order

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_TYPE
import com.example.wp.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_order.*
import java.util.ArrayList

class OrderFragment : Fragment() {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menus = arguments?.getParcelableArrayList<DataItem>(AppConstants.KEY_MENU).orEmpty()

        showMenus()
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