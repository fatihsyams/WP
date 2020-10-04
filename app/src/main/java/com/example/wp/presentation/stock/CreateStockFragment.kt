package com.example.wp.presentation.stock

import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.utils.*
import kotlinx.android.synthetic.main.fragment_create_stock.*
import kotlinx.android.synthetic.main.fragment_order.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateStockFragment : WarungPojokFragment() {

    private val materialViewModel: MaterialViewModel by viewModel()

    var onStockListener:OnStockListener? = null

    override val layoutView: Int = R.layout.fragment_create_stock

    override fun onPreparation() {
    }

    override fun onIntent() {
    }

    override fun onView() {
    }

    override fun onAction() {
        btnCreateStock.setOnClickListener { submitMaterial() }
    }

    override fun onObserver() {
        materialViewModel.materialLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> pbMaterial.visible()
                is Load.Fail -> {
                    pbMaterial.gone()
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    pbMaterial.visible()
                    showToast("Berhasil menambahkan stok")
                    onStockListener?.onSubmitStock()
                }
            }
        })
    }

    private fun submitMaterial() {
        materialViewModel.postMaterial(
            material = Material(
                material = edtMaterial.text.toString(),
                stock = edtStock.text.toString().toIntOrNull() ?: 0
            )
        )
    }

    interface OnStockListener{
        fun onSubmitStock()
    }

}