package com.example.wp.presentation.stock.checkstock

import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.presentation.adapter.CheckStockAdapter
import com.example.wp.presentation.listener.StockListener
import com.example.wp.presentation.stock.StockContainerFragment
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.utils.Load
import com.example.wp.utils.showContentView
import com.example.wp.utils.showLoadingView
import com.example.wp.utils.showToast
import kotlinx.android.synthetic.main.fragment_check_stock.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckStockFragment : WarungPojokFragment(), StockListener {

    private val materialViewModel: MaterialViewModel by viewModel()

    override val layoutView: Int = R.layout.fragment_check_stock

    override fun onPreparation() {
        (parentFragment as StockContainerFragment).onStockListener = this
    }

    override fun onIntent() {
    }

    override fun onView() {
    }

    override fun onAction() {
    }

    override fun onObserver() {
        materialViewModel.getMaterialLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> msvMaterial.showLoadingView()
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    msvMaterial.showContentView()
                    showData(it.data)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getMaterials()
    }

    private fun getMaterials() {
        materialViewModel.getMaterials()
    }

    private fun showData(data: List<Material>) {
        rvCheckStok.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CheckStockAdapter(context, data, this@CheckStockFragment)
        }
    }

    override fun onSaveStockClicked(material: Material) {
        materialViewModel.editMaterial(
            material = material,
            materialId = material.id
        )
    }

    override fun onStockCreated() {
        getMaterials()
    }

}