package com.example.wp.presentation.order

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.menu.getTakeAwayTypes
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.table.Table
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_EDIT_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_GO_FOOD_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_GRAB_FOOD_TYPE
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_READ_TYPE
import com.example.wp.presentation.adapter.PaymentAdapter
import com.example.wp.presentation.adapter.TableAdapter
import com.example.wp.presentation.adapter.TakeAwayAdapter
import com.example.wp.presentation.listener.*
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.OrderViewModel
import com.example.wp.presentation.viewmodel.PaymentViewModel
import com.example.wp.presentation.viewmodel.TableViewModel
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants
import com.example.wp.utils.constants.AppConstants.KEY_MENU
import com.example.wp.utils.constants.AppConstants.KEY_ORDER
import com.example.wp.utils.datepicker.DialogDatePicker
import com.example.wp.utils.enum.OrderNameTypeEnum
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.enum.TakeAwayTypeEnum
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class OrderFragment : WarungPojokFragment(), CalculateMenuListener {

    companion object {
        @JvmStatic
        fun newInstance(
            orderResult: OrderResult? = null,
            menus: List<Menu>,
            isEditMode: Boolean = false
        ) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ORDER, orderResult)
                    putParcelableArrayList(KEY_MENU, menus as ArrayList<Menu>)
                    putBoolean(AppConstants.KEY_ORDER_MODE, isEditMode)
                }
            }
    }

    private val orderViewModel: OrderViewModel by viewModel()
    private val tableViewModel: TableViewModel by viewModel()
    private val paymentViewModel: PaymentViewModel by viewModel()

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
    private var selectedPayment: Payment? = null
    private var selectedTakeAway: TakeAway? = null
    private var orderResult: OrderResult? = null

    private var isEditMode = false

    private var discount = 0
    private var selectedOrderType = 0
    private var totalPayment = 0.0
    private var totalPaymentBeforeDiscount = 0.0
    private var orderId = 0

    private var selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override val layoutView: Int = R.layout.fragment_order

    override fun onPreparation() {
        progressDialog.setCancelable(false)
    }

    override fun onIntent() {
        orderResult = arguments?.getParcelable(KEY_ORDER)
        menus = arguments?.getParcelableArrayList<Menu>(KEY_MENU) ?: mutableListOf()
        isEditMode = arguments?.getBoolean(AppConstants.KEY_ORDER_MODE, false) ?: false
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        showTotalPrice(selectedOrderNameType)
        orderTypeContainer.visible()
        btnAdd.visible()
        showMenus()
        showOrderResult()
    }

    override fun onAction() {
        btnDineIn.setOnClickListener {
            selectedOrderType = OrderTypeEnum.DINE_IN.type
            selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type
            onDineInSelected()
        }

        btnTakeAway.setOnClickListener {
            selectedOrderType = OrderTypeEnum.TAKE_AWAY.type
            onTakeAwaySelected()
        }

        btnPreOrder.setOnClickListener {
            selectedOrderType = OrderTypeEnum.PRE_ORDER.type
            onPreOrderSelected()
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
            onAddMenuListener?.onOpenMenuPage(menus)
        }

        edtPickupOrder.setOnClickListener {
            DialogDatePicker(requireContext(), object : DialogDatePicker.DateDialogListener {
                override fun onSelectDate(date: String) {
                    edtPickupOrder.setText(date)
                }
            }).show()
        }

        btnPrint.setOnClickListener {
            if (isOrderValid()) {
                if (isEditMode) orderViewModel.editOrder(getOrderResult()) else orderViewModel.postOrder(
                    getOrderResult()
                )
            }
        }

        edtDiscount.doOnTextChanged { text, _, _, _ ->
            Handler().postDelayed({
                if (!text.isNullOrEmpty()) discount = text.toString().toInt()
                showTotalPrice(selectedOrderNameType)
            }, 1000)
        }

        btnPayment.setOnClickListener {
            paymentViewModel.getPayments()
        }
    }

    private fun onDineInSelected() {
        dineInContainer.visible()
        orderTypeContainer.gone()
        btnEditOrderType.visible()
    }

    private fun onPreOrderSelected() {
        preOrderContainer.visible()
        orderTypeContainer.gone()
        btnEditOrderType.visible()
    }

    private fun onTakeAwaySelected() {
        takeAwayContainer.visible()
        orderTypeContainer.gone()
        btnEditOrderType.visible()
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
                    progressDialog.dismiss()
                    (activity as MainActivity).clearSelectedMenus()
                    (activity as MainActivity).toOrderListFragment()
                }
            }
        })

        orderViewModel.editOrderLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Loading -> progressDialog.show()
                is Load.Fail -> {
                    progressDialog.dismiss()
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    progressDialog.dismiss()
                    (activity as MainActivity).clearSelectedMenus()
                    (activity as MainActivity).toOrderListFragment()
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
                is Load.Loading -> {
                }
            }
        })

        paymentViewModel.paymentLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showPaymentOptions(it.data)
                }
                is Load.Loading -> {
                }
            }
        })

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
                id = orderId,
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
            type = selectedOrderType,
            paymentMethod = selectedPayment?.name.orEmpty()
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

    private fun showOrderResult() {
        orderResult?.let { order ->
            orderId = order.order.id
            selectedOrderType = order.type
            selectedPayment = Payment(order.paymentMethod)
//            menus = order.menu.toMutableList()
            when (selectedOrderType) {
                OrderTypeEnum.TAKE_AWAY.type -> {
                    onTakeAwaySelected()
                    btnTakeAwayType.text = order.order.orderCategory
                    when (order.order.orderCategory) {
                        TakeAwayTypeEnum.GRABFOOD.type -> {
                            selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GRABFOOD.type
                            menuAdapter.updateOrderReadType(ORDER_READ_GRAB_FOOD_TYPE)
                        }
                        TakeAwayTypeEnum.GOFOOD.type -> {
                            selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GOFOOD.type
                            menuAdapter.updateOrderReadType(ORDER_READ_GO_FOOD_TYPE)
                        }
                        TakeAwayTypeEnum.PERSONAL.type -> {
                            selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY.type
                            menuAdapter.updateOrderReadType(ORDER_READ_TYPE)
                        }
                    }
                }
                OrderTypeEnum.DINE_IN.type -> {
                    selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type
                    onDineInSelected()
                    val table = order.order.tableId?.toInt() ?: 0
                    selectedTable = Table(id = table, number = table)
                    btnTableNumber.text = order.order.tableId
                }
                OrderTypeEnum.PRE_ORDER.type -> {
                    selectedOrderNameType = OrderNameTypeEnum.PRE_ORDER.type
                    onPreOrderSelected()
                    btnPreOrder.text = order.order.orderCategory
                }
            }
            edtCustomerName.setText(order.order.customerName)
            btnPayment.text =
                if (order.paymentMethod.isEmpty()) getString(R.string.action_select_payment_method) else order.paymentMethod
            if (order.order.discount != 0) {
                edtDiscount.setText(order.order.discount.toString())
            }
        }
    }

    private fun showMenus() {
        menuAdapter.type = ORDER_EDIT_TYPE
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

    private fun showPaymentOptions(payments: List<Payment>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val paymentAdapter = PaymentAdapter(
                context = requireContext(),
                datas = payments,
                listener = object : PaymentListener {
                    override fun onPaymentSelected(data: Payment) {
                        selectedPayment = data
                        getSelectedPayment()
                        dismiss()
                    }
                }
            )

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = paymentAdapter
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
        menuAdapter.updateDataMenu(menus)
    }

    override fun onPlusClicked(menu: Menu, position: Int) {
        if ((menu.quantity * menu.stockRequired).toDouble() == menu.stock) showToast(getString(R.string.message_error_over_stock))
        showTotalPrice(selectedOrderNameType)
    }

    override fun onMinuslicked(menu: Menu, position: Int) {
        showTotalPrice(selectedOrderNameType)
    }

    fun getSelectedTable() {
        btnTableNumber.text = selectedTable?.number.toString()
    }

    fun getSelectedPayment() {
        btnPayment.text = selectedPayment?.name
    }

    fun getSelectedTakeAway() {
        btnTakeAwayType.text = selectedTakeAway?.name.orEmpty()
        when (selectedTakeAway?.name) {
            TakeAwayTypeEnum.GOFOOD.type -> {
                selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GOFOOD.type
                menuAdapter.updateOrderReadType(ORDER_READ_GO_FOOD_TYPE)
                edtCustomerName.setText(selectedOrderNameType.toUpperCase(Locale.getDefault()))
            }
            TakeAwayTypeEnum.GRABFOOD.type -> {
                selectedOrderNameType = OrderNameTypeEnum.TAKE_AWAY_GRABFOOD.type
                menuAdapter.updateOrderReadType(ORDER_READ_GRAB_FOOD_TYPE)
                edtCustomerName.setText(selectedOrderNameType.toUpperCase(Locale.getDefault()))
            }
            else -> {
                selectedOrderNameType = OrderNameTypeEnum.DINE_IN.type
                menuAdapter.updateOrderReadType(ORDER_EDIT_TYPE)
            }
        }
        showTotalPrice(selectedOrderNameType)
    }

}