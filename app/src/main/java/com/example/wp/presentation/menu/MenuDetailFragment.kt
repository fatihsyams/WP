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
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.utils.AppConstants.KEY_MENU
import com.example.wp.utils.generateCustomAlertDialog
import com.example.wp.utils.removeFragment
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.tvPrice
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.*

class MenuDetailFragment : WarungPojokFragment() {

    companion object {
        fun newInstance(menu: DataItem) =
            MenuDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MENU, menu)
                }
            }
    }

    private var menu: DataItem? = null

    private var quantity = 0

    var onMenuSelectListener: MenuListener? = null

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
        btnMinus.setOnClickListener {
            if (quantity > 0) quantity--
            setMenuQuantity()
        }

        btnPlus.setOnClickListener {
            quantity++
            setMenuQuantity()
        }

        btnOk.setOnClickListener { showAdditionalNoteDialog() }
    }

    private fun setMenuQuantity(){
        tvQuantity.text = quantity.toString()
    }

    override fun onObserver() {
    }

    private fun showMenuDetail() {
        menu?.apply {
            if (!images.isNullOrEmpty()) {
                Glide.with(this@MenuDetailFragment).load(images.first().imageUrl).into(imgMenu)
            }
            tvDescription.text = description
            tvPrice.text = "Rp $price"
        }
    }

    private fun showAdditionalNoteDialog() {
        context?.let {
            generateCustomAlertDialog(
                it,
                R.layout.layout_order_additional_note_dialog,
                false
            ).apply {

                menu?.apply {
                    tvNamaMenu.text = name
                    tvPrice.text = "Rp $price"

                    btnCancel.setOnClickListener { dismiss() }

                    btnDone.setOnClickListener {
                        this.quantity = quantity
                        additionalInformation = edtNote.text.toString()
                        onMenuSelectListener?.onSelectMenu(this)
                        dismiss()
                        removeFragment()
                    }
                }

            }
        }
    }

}