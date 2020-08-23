package com.example.wp.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.utils.AppConstants.KEY_MENU
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuDetailFragment : WarungPojokFragment() {

    companion object {
        fun newInstance(menu: DataItem) =
            MenuDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MENU, menu)
                }
            }
    }

    private var menu:DataItem? = null

    override val layoutView: Int = R.layout.fragment_menu_detail

    override fun onPreparation() {
    }

    override fun onIntent() {
        menu = arguments?.getParcelable(KEY_MENU)
    }

    override fun onView() {
        showMenuDetail()
    }

    override fun onAction() {
    }

    override fun onObserver() {
    }

    private fun showMenuDetail(){
        menu?.apply {
            if (!images.isNullOrEmpty()) {
                Glide.with(this@MenuDetailFragment).load(images.first().imageUrl).into(imgMenu)
            }
            tvDescription.text  = description
            tvPrice.text = "Rp $price"
        }
    }

}