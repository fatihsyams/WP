package com.example.wp.presentation.menu

import android.os.Bundle
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.utils.AppConstants.KEY_MENU
import com.example.wp.utils.generateCustomAlertDialog
import com.example.wp.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.tvPrice
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.*

class MenuDetailFragment : WarungPojokFragment() {

    companion object {
        fun newInstance(menu: Menu) =
            MenuDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_MENU, menu)
                }
            }
    }

    private var menu: Menu? = null

    private var quantity = 1

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
            if (quantity > 1) quantity--
            setMenuQuantity()
        }

        btnPlus.setOnClickListener {
            quantity++
            setMenuQuantity()
        }

        btnOk.setOnClickListener { showAdditionalNoteDialog() }
    }

    private fun setMenuQuantity() {
        tvQuantity.text = quantity.toString()
        val totalPrice = menu?.price?.times(quantity)
        tvPrice.text = "Rp $totalPrice"
    }

    override fun onObserver() {
    }

    private fun showMenuDetail() {
        menu?.apply {
            if (!images.isNullOrEmpty()) {
                Glide.with(this@MenuDetailFragment).load(images).into(imgMenu)
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