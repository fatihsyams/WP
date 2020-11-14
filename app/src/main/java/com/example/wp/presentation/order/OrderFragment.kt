package com.example.wp.presentation.order

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.R
import com.example.wp.base.WarungPojokPrinterFragment
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.menu.getTakeAwayTypes
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.table.Table
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_EDIT_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_GO_FOOD_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_GRAB_FOOD_TYPE
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
import com.example.wp.utils.datepicker.DialogDatePicker
import com.example.wp.utils.enum.OrderNameTypeEnum
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.enum.TakeAwayTypeEnum
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import kotlinx.android.synthetic.main.layout_alert_option.tvTitle
import kotlinx.android.synthetic.main.layout_print_dialog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class OrderFragment : WarungPojokPrinterFragment(), CalculateMenuListener {

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

    private val scope = CoroutineScope(Dispatchers.IO)

    private val menuAdapter: MenusAdapter by lazy {
        MenusAdapter(
            context = requireContext(),
            data = menus.distinct(),
            type = ORDER_EDIT_TYPE,
            onCalculateMenuListener = this
        )
    }

    private var menus = mutableListOf<Menu>()

    var onAddMenuListener: OpenMenuPageListener? = null
    private var selectedTable: Table? = null
    private var selectedTakeAway: TakeAway? = null

    private var orderMode = EDIT_MODE

    private var discount = 0
    private var selectedOrderType = 0
    private var totalPayment = 0.0
    private var totalPaymentBeforeDiscount = 0.0

    private var selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type

    override var order: OrderResult = OrderResult()

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override val layoutView: Int = R.layout.fragment_order

    override fun onPreparation() {
        progressDialog.setCancelable(false)
    }

    override fun onIntent() {
        menus =
            arguments?.getParcelableArrayList<Menu>(AppConstants.KEY_MENU) ?: mutableListOf()

        orderMode = arguments?.getInt(AppConstants.KEY_ORDER_MODE) ?: EDIT_MODE
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        showTotalPrice(order.order.orderCategory)
        orderTypeContainer.visible()
        btnAdd.visible()
        btnPrint.text = "Submit"
        showMenus(ORDER_EDIT_TYPE)
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
            selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type
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
            selectedOrderType = ORDER_EDIT_TYPE
            menuAdapter.updateOrderReadType(ORDER_EDIT_TYPE)
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

        edtDiscount.doOnTextChanged { text, start, before, count ->
            Handler().postDelayed({
                if (!text.isNullOrEmpty()) discount = text.toString().toInt()
                showTotalPrice(selectedOrderNameType)
            }, 1000)
        }
    }

    override fun onObserver() {
        orderViewModel.orderLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> progressDialog.show()
                is Load.Fail -> {
                    progressDialog.dismiss()
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    order = getOrderResult()
                    printReceipe()
                }
            }
        })

        tableViewModel.tablesLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showTableOptions(it.data)
                }
            }        })

    }

    private fun printReceipe() {
        scope.launch {
            Log.d("PRINT", "start print...")
            withContext(Dispatchers.Default) {
                printBluetooth()
            }
        }
        showPrintAlert()
    }

    private fun showPrintAlert() {
        progressDialog.dismiss()
        generateCustomAlertDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_print_dialog,
            isCancelable = false
        ).apply {

            tvTitle.text = getString(R.string.title_receipt_printed)
            tvMessage.text = getString(R.string.message_receipt_printed)

            btnNegative.setOnClickListener {
                dismiss()
                onPrintFinish()
            }

            btnPositive.setOnClickListener {
                dismiss()
                progressDialog.show()

                val handler = Handler()
                val delayTime = 5000L
                handler.postDelayed({
                    Log.d("PRINT", "preparing print again...")
                    printReceipe()
                }, delayTime)
            }
        }
    }

    private fun onPrintFinish() {
        if (progressDialog.isShowing) progressDialog.dismiss()
        (activity as MainActivity).clearSelectedMenus()
        removeFragment()
    }

    private fun isOrderValid(): Boolean {
        var valid = true
        if (selectedOrderType == 0) {
            valid = false
            showToast(getString(R.string.message_select_order_type_first))
        } else if (selectedOrderType == OrderTypeEnum.DINE_IN.type && selectedTable == null) {
            valid = false
            showToast(getString(R.string.message_select_table_first))
        } else if (selectedOrderType == OrderTypeEnum.TAKE_AWAY.type && selectedTakeAway == null) {
            valid = false
            showToast(getString(R.string.message_select_take_away_type_first))
        } else if (selectedOrderType == OrderTypeEnum.PRE_ORDER.type && edtPickupOrder.text.isNullOrEmpty()) {
            valid = false
            showToast(getString(R.string.message_select_delivery_time_first))
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
                        OrderNameTypeEnum.DINE_IN.type
                    }
                    OrderTypeEnum.PRE_ORDER.type -> {
                        OrderNameTypeEnum.PRE_ORDER.type
                    }
                    else -> OrderNameTypeEnum.TAKE_AWAY.type
                },
                tableId = if (selectedOrderType == OrderTypeEnum.DINE_IN.type) btnTableNumber.text.toString() else null,
                discount = discount,
                totalPayment = totalPayment,
                totalPaymentBeforeDiscount = totalPaymentBeforeDiscount
            ),
            menu = menuAdapter.data,
            type = selectedOrderType
        )
    }

    private fun showTotalPrice(orderType: String) {
        totalPaymentBeforeDiscount = menus.map {
            when (orderType) {
                OrderNameTypeEnum.DINE_IN.type -> it.price * it.quantity
                OrderNameTypeEnum.TAKE_AWAY_GOFOOD.type -> it.goFoodPrice * it.quantity
                OrderNameTypeEnum.TAKE_AWAY_GRABFOOD.type -> it.grabFoodPrice * it.quantity
                else -> it.totalPrice
            }
        }.sum()
        val totalDiscount = totalPaymentBeforeDiscount.times(discount) / 100
        totalPayment = totalPaymentBeforeDiscount - totalDiscount
        tvTotalPrice.text = toCurrencyFormat(totalPayment)
    }

    private fun showMenus(orderType: Int) {
        menuAdapter.type = orderType
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
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val takeAwayAdapter = TakeAwayAdapter(
                context = requireContext(),
                datas = getTakeAwayTypes(),
                listener = object : TakeAwayListener {
                    override fun onTakeAwaySelected(data: TakeAway) {
                        selectedTakeAway = data
                        getSelectedTakeAway()
                        dismiss()
                    }
                }
            )

            tvTitle.text = getString(R.string.title_select_take_away_type)

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = takeAwayAdapter
            }
        }
    }


    override fun onDeleteClicked(menu: Menu, position: Int) {
        menus.remove(menu)
        showTotalPrice(selectedOrderNameType)
    }

    override fun onPlusClicked(menu: Menu, position: Int) {
        if (menu.quantity * menu.stockRequired == menu.stock) showToast(getString(R.string.message_error_over_stock))
        showTotalPrice(selectedOrderNameType)
    }

    override fun onMinuslicked(menu: Menu, position: Int) {
        showTotalPrice(selectedOrderNameType)
    }

    fun getSelectedTable() {
        btnTableNumber.text = selectedTable?.number.toString()
    }

    fun getSelectedTakeAway() {
        btnTakeAwayType.text = selectedTakeAway?.name.orEmpty()
        when (selectedTakeAway?.name) {
            TakeAwayTypeEnum.GOFOOD.type -> {
                selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GOFOOD.type
                menuAdapter.updateOrderReadType(ORDER_READ_GO_FOOD_TYPE)
            }
            TakeAwayTypeEnum.GRABFOOD.type -> {
                selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GRABFOOD.type
                menuAdapter.updateOrderReadType(ORDER_READ_GRAB_FOOD_TYPE)
            }
            else -> {
                selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type
                menuAdapter.updateOrderReadType(ORDER_EDIT_TYPE)
            }
        }
        showTotalPrice(selectedOrderNameType)
    }

}