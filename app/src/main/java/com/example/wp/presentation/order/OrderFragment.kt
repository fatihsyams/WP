package com.example.wp.presentation.order

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.table.Table
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_EDIT_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_TYPE
import com.example.wp.presentation.adapter.TableAdapter
import com.example.wp.presentation.adapter.TakeAwayAdapter
import com.example.wp.presentation.listener.CalculateMenuListener
import com.example.wp.presentation.listener.OpenMenuPageListener
import com.example.wp.presentation.listener.TableListener
import com.example.wp.presentation.listener.TakeAwayListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.OrderViewModel
import com.example.wp.presentation.viewmodel.TableViewModel
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants
import com.example.wp.utils.datePicker.DialogDatePicker
import com.example.wp.utils.enum.OrderTypeEnum
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class OrderFragment : WarungPojokFragment(), CalculateMenuListener {

    companion object {
        const val EDIT_MODE = 304
        const val READ_MODE = 302

        @JvmStatic
        fun newInstance(menus: List<Menu>, mode: Int = EDIT_MODE) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(AppConstants.KEY_MENU, menus as ArrayList<Menu>)
                    putInt(AppConstants.KEY_ORDER_MODE, mode)
                }
            }
    }

    private val orderViewModel: OrderViewModel by viewModel()
    private val tableViewModel: TableViewModel by viewModel()

    private var menus = mutableListOf<Menu>()

    var onAddMenuListener: OpenMenuPageListener? = null

    private var selectedOrderType = 0

    private var selectedTable: Table? = null

    private var selectedTakeAway: TakeAway? = null

    private var orderMode = EDIT_MODE

    override val layoutView: Int = R.layout.fragment_order

    override fun onPreparation() {
    }

    override fun onIntent() {
        menus =
            arguments?.getParcelableArrayList<Menu>(AppConstants.KEY_MENU) ?: mutableListOf()

        orderMode = arguments?.getInt(AppConstants.KEY_ORDER_MODE) ?: EDIT_MODE
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        showTotalPrice()
        setupOrderView()
    }

    private fun setupOrderView() {
        when (orderMode) {
            EDIT_MODE -> {
                orderTypeContainer.visible()
                btnAdd.visible()
                btnPrint.text = "Submit"
                showMenus(ORDER_EDIT_TYPE)
            }
            READ_MODE -> {
                orderTypeContainer.gone()
                btnAdd.gone()
                btnPrint.text = "Cetak"
                showMenus(ORDER_READ_TYPE)
            }
        }
    }

    override fun onAction() {
        btnDineIn.setOnClickListener {
            selectedOrderType = OrderTypeEnum.DINE_IN.type
            dineInContainer.visible()
            orderTypeContainer.gone()
            btnEditOrderType.visible()
        }

        btnTakeAway.setOnClickListener {
            selectedOrderType = OrderTypeEnum.TAKE_AWAY.type
            takeAwayContainer.visible()
            orderTypeContainer.gone()
            btnEditOrderType.visible()
        }

        btnPreOrder.setOnClickListener {
            selectedOrderType = OrderTypeEnum.PRE_ORDER.type
            preOrderContainer.visible()
            orderTypeContainer.gone()
            btnEditOrderType.visible()
        }

        btnEditOrderType.setOnClickListener {
            btnEditOrderType.gone()
            orderTypeContainer.visible()
            when (selectedOrderType) {
                OrderTypeEnum.DINE_IN.type -> dineInContainer.invisible()
                OrderTypeEnum.TAKE_AWAY.type -> takeAwayContainer.invisible()
                OrderTypeEnum.PRE_ORDER.type -> preOrderContainer.invisible()
            }
            selectedOrderType = 0
        }

        btnTableNumber.setOnClickListener { tableViewModel.getTables() }

        btnTakeAwayType.setOnClickListener { showTakeAwayOptions() }

        btnAdd.setOnClickListener {
            onAddMenuListener?.onOpenMenuPage()
        }

        edtPickupOrder.setOnClickListener {
            DialogDatePicker(requireContext(), object : DialogDatePicker.DateDialogListener {
                override fun onSelectDate(date: String) {
                    edtPickupOrder.setText(date)
                }
            }).show()
        }

        btnPrint.setOnClickListener {
            if (isOrderValid()) orderViewModel.postOrder(getOrderResult())
        }
    }

    override fun onObserver() {
        orderViewModel.orderLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> pbOrder.visible()
                is Load.Fail -> {
                    pbOrder.gone()
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    pbOrder.visible()
                    showToast("Berhasil Submit Order")
                    removeFragment()
                }
            }
        })

        tableViewModel.tablesLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage ?: "Error tidak diketahui")
                }
                is Load.Success -> {
                    showTableOptions(it.data)
                }
            }
        })

    }

    private fun isOrderValid(): Boolean {
        var valid = true
        if (selectedOrderType == 0) {
            valid = false
            showToast("Pilih Tipe Pesanan Terlebih Dahulu")
        } else if (selectedOrderType == OrderTypeEnum.DINE_IN.type && selectedTable == null) {
            valid = false
            showToast("Pilih Nomer Meja Terlebih Dahulu")
        } else if (selectedOrderType == OrderTypeEnum.TAKE_AWAY.type && selectedTakeAway == null) {
            valid = false
            showToast("Pilih Jenis Take Away Terlebih Dahulu")
        } else if (selectedOrderType == OrderTypeEnum.PRE_ORDER.type && edtPickupOrder.text.isNullOrEmpty()) {
            valid = false
            showToast("Pilih Tanggal Pengambilan Pesanan Terlebih Dahulu")
        }
        return valid
    }

    private fun getOrderResult(): OrderResult {
        return OrderResult(
            order = Order(
                customerName = edtCustomerName.text.toString(),
                orderCategory = when (selectedOrderType) {
                    OrderTypeEnum.TAKE_AWAY.type -> {
                        btnTakeAwayType.text.toString()
                    }
                    OrderTypeEnum.DINE_IN.type -> {
                        "dine in"
                    }
                    OrderTypeEnum.PRE_ORDER.type -> {
                        "po"
                    }
                    else -> "take away"
                },
                tableId = if (selectedOrderType == OrderTypeEnum.DINE_IN.type) btnTableNumber.text.toString() else null
            ),
            menu = menus
        )
    }

    private fun showTotalPrice() {
        val totalPrice = menus.map { it.price.times(it.quantity) }.sum()
        tvTotalPrice.text = "Rp $totalPrice"
    }

    private fun showMenus(orderType: Int) {

        val menuAdapter = MenusAdapter(
            context = requireContext(),
            data = menus,
            type = orderType,
            onCalculateMenuListener = this
        )

        rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }

    }

    private fun showTableOptions(tables: List<Table>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val tableAdapter = TableAdapter(
                context = requireContext(),
                datas = tables,
                listener = object : TableListener {
                    override fun onTableSelected(data: Table) {
                        selectedTable = data
                        getSelectedTable()
                        dismiss()
                    }
                }
            )

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = tableAdapter
            }
        }
    }

    private fun showTakeAwayOptions() {
        val takeAways = mutableListOf(
            TakeAway(
                "Gojek",
                "https://cdn2.tstatic.net/jakarta/foto/bank/images/logo-gojek-baru.jpg"
            ),
            TakeAway(
                "Grab",
                "https://media.suara.com/pictures/970x544/2016/08/22/o_1aqpc505a7vr17au5e0s3ifgka.jpg"
            ),
            TakeAway(
                "Personal",
                "https://icon-library.com/images/personal-icon/personal-icon-4.jpg"
            )
        )

        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val takeAwayAdapter = TakeAwayAdapter(
                context = requireContext(),
                datas = takeAways,
                listener = object : TakeAwayListener {
                    override fun onTakeAwaySelected(data: TakeAway) {
                        selectedTakeAway = data
                        getSelectedTakeAway()
                        dismiss()
                    }
                }
            )

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = takeAwayAdapter
            }
        }
    }


    override fun onDeleteClicked(menu: Menu, position: Int) {
        menus.remove(menu)
        showTotalPrice()
    }

    override fun onPlusClicked(menu: Menu, position: Int) {
        showTotalPrice()
    }

    override fun onMinuslicked(menu: Menu, position: Int) {
        showTotalPrice()
    }

    fun getSelectedTable() {
        btnTableNumber.text = selectedTable?.number.toString()
    }

    fun getSelectedTakeAway() {
        btnTakeAwayType.text = selectedTakeAway?.name.orEmpty()
    }

}