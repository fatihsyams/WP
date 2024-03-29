package com.example.wp.presentation.menu

import android.os.Bundle
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.bumptech.glide.Glide
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants.KEY_MENU
import kotlinx.android.synthetic.main.fragment_menu_detail.*
import kotlinx.android.synthetic.main.fragment_menu_detail.tvPrice

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
            menu?.let { menu ->
                    selectedQuantity--
                setMenuQuantity()
            }
        }

        btnPlus.setOnClickListener {
            menu?.let { menu ->
                    selectedQuantity++
                    setMenuQuantity()
            }
        }

        btnOk.setOnClickListener {
            menu?.apply {
                    this.quantity = selectedQuantity
                    totalPrice = (menuPrice.firstOrNull()?.price?.toDouble()?: 0.0 )* selectedQuantity
                    additionalInformation = edtKeteranganOrder.text.toString()
                    onMenuSelectListener?.onSelectMenu(this)
            }
        }
    }


    private fun setMenuQuantity() {
        tvQuantity.text = selectedQuantity.toString()
        val price = menu?.menuPrice?.firstOrNull()?.price?.toDouble() ?: 0.0
        val totalPrice = price.times(selectedQuantity)
        tvPrice.text = toCurrencyFormat(totalPrice ?: 0.0)
    }

    override fun onObserver() {
    }

    private fun showMenuDetail() {
        menu?.apply {
            if (images.isNotEmpty()) {
                Glide.with(this@MenuDetailFragment).load(images).into(imgMenu)
            }
            tvName.text = name
            tvDescription.text = description
            tvPrice.text = toCurrencyFormat(menuPrice.firstOrNull()?.price?.toDouble() ?: 0.0)
        }
    }

//    private fun showAdditionalNoteDialog() {
//        context?.let {
//            generateCustomAlertDialog(
//                it,
//                R.layout.layout_order_additional_note_dialog,
//                false
//            ).apply {
//
//                menu?.apply {
//                    tvNamaMenu.text = name
//                    tvPrice.text = toCurrencyFormat(price)
//
//                    btnCancel.setOnClickListener { dismiss() }
//
//                    btnDone.setOnClickListener {
//                        this.quantity = selectedQuantity
//                        totalPrice = price * selectedQuantity
//                        additionalInformation = edtNote.text.toString()
//                        onMenuSelectListener?.onSelectMenu(this)
//                        dismiss()
//                        removeFragment()
//                    }
//                }
//
//            }
//        }
//    }

}