package com.example.wp.presentation.stock.checkstock

import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.material.Material
import com.example.wp.presentation.adapter.CheckStockAdapter
import com.example.wp.presentation.listener.StockListener
import com.example.wp.presentation.stock.StockContainerFragment
import com.example.wp.presentation.viewmodel.MaterialViewModel
import com.example.wp.utils.*
import com.example.wp.utils.enum.UpdateStockTypeEnum
import kotlinx.android.synthetic.main.fragment_check_stock.*
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.*
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.btnCancel
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.btnDone
import kotlinx.android.synthetic.main.layout_order_additional_note_dialog.edtNote
import kotlinx.android.synthetic.main.layout_update_stock_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckStockFragment : WarungPojokFragment(), StockListener {

    private val materialViewModel: MaterialViewModel by viewModel()

    override val layoutView: Int = R.layout.fragment_check_stock

    private var isIncrease = false

    private var updatedQuantity = 0.0
    private var updatedPosition = 0

    private var updatedMaterial:Material = Material()

    private val stockAdapter:CheckStockAdapter by lazy { CheckStockAdapter(requireContext(), mutableListOf(), this@CheckStockFragment) }

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
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    msvMaterial.showContentView()
                    showData(it.data)
                }
            }
        })

        materialViewModel.updateMaterialLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> pbStock.visible()
                is Load.Fail -> {
                    pbStock.gone()
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    pbStock.gone()
                    showToast(getString(R.string.message_success_update_stock))
                    stockAdapter.notifyMenuUpdated(updatedPosition,updatedMaterial)
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
        stockAdapter.data = data
        rvCheckStok.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = stockAdapter
        }
    }

    override fun onSaveStockClicked(position:Int,material: Material) {
        updatedPosition = position
        updatedMaterial = material
        showUpdateStockAlertDialog(material)
    }

    override fun onStockCreated() {
        getMaterials()
    }

    private fun showUpdateStockAlertDialog(material:Material){
        generateCustomAlertDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_update_stock_dialog,
            isCancelable = false
        ).apply {

            btnCancel.setOnClickListener { dismiss() }

            btnDone.setOnClickListener {
                materialViewModel.updateMaterial(
                    materialId = material.id,
                    stock = if(isIncrease) material.increasedQuantity else material.decreasedQuantity,
                    reason = edtNote.text.toString(),
                    type = if(isIncrease) UpdateStockTypeEnum.INCREASE.type else UpdateStockTypeEnum.DECREASE.type
                )
                dismiss()
            }

        }
    }

    override fun onIncreaseStockClicked(isIncreased:Boolean, increasedQuantity: Double) {
        isIncrease = isIncreased
        updatedQuantity = increasedQuantity
    }

    override fun onDecreaseStockClicked(isDecreased:Boolean, decreasedQuantity: Double) {
        isIncrease != isDecreased
        updatedQuantity = decreasedQuantity
    }
}