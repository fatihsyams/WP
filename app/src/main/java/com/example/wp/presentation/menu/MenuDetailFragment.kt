package com.example.wp.presentation.menu

import android.os.Bundle
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.utils.constants.AppConstants.KEY_MENU
import com.example.wp.utils.generateCustomAlertDialog
import com.example.wp.utils.gone
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

    private var selectedQuantity = 1

    var onMenuSelectListener: MenuListener? = null

    override val layoutView: Int = R.layout.fragment_menu_detail

    override fun onPreparation() {
    }

    override fun onIntent() {
        menu = arguments?.getParcelable(KEY_MENU)
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        showMenuDetail()
    }

    override fun onAction() {
        btnMinus.setOnClickListener {
            if (selectedQuantity > 1) selectedQuantity--
            setMenuQuantity()
        }

        btnPlus.setOnClickListener {
            selectedQuantity++
            setMenuQuantity()
        }

        btnOk.setOnClickListener { showAdditionalNoteDialog() }
    }

    private fun setMenuQuantity(){
        tvQuantity.text = selectedQuantity.toString()
        val totalPrice = menu?.price?.times(selectedQuantity)
        tvPrice.text = "Rp $totalPrice"
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
                        this.quantity = selectedQuantity
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