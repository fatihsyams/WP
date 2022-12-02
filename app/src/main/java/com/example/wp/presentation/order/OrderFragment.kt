package com.example.wp.presentation.order

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.order.Customer
import com.example.wp.domain.order.Order
import com.example.wp.domain.order.OrderResult
import com.example.wp.domain.order.Wallet
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.table.Table
import com.example.wp.presentation.adapter.*
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_EDIT_TYPE
import com.example.wp.presentation.listener.*
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.OrderViewModel
import com.example.wp.presentation.viewmodel.PaymentViewModel
import com.example.wp.presentation.viewmodel.TableViewModel
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants
import com.example.wp.utils.constants.AppConstants.KEY_KATEGORI
import com.example.wp.utils.constants.AppConstants.KEY_MENU
import com.example.wp.utils.constants.AppConstants.KEY_ORDER
import com.example.wp.utils.datepicker.DialogDatePicker
import com.example.wp.utils.enum.OrderNameTypeEnum
import com.example.wp.utils.enum.OrderTypeEnum
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderFragment : WarungPojokFragment(), CalculateMenuListener {

    companion object {
        @JvmStatic
        fun newInstance(
            orderResult: OrderResult? = null,
            menus: List<Menu>,
            isEditMode: Boolean = false,
            kategoriOrder: KategoriOrder
        ) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ORDER, orderResult)
                    putParcelableArrayList(KEY_MENU, menus as ArrayList<Menu>)
                    putBoolean(AppConstants.KEY_ORDER_MODE, isEditMode)
                    putParcelable(KEY_KATEGORI, kategoriOrder)
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
    private var selectedKas: Wallet? = null
    private var selectedPelanggan: Customer? = null
    private var selectedTakeAway: TakeAway? = null
    private var orderResult: OrderResult? = null
    private var selectedCategoryOrder: KategoriOrder? = null
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
        selectedCategoryOrder = arguments?.getParcelable(KEY_KATEGORI)
        selectedOrderNameType = selectedCategoryOrder?.name.orEmpty()
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        btnAdd.visible()
        showMenus()
        showOrderResult()
        showTotalPrice()
    }

    override fun onAction() {

        btnTableNumber.setOnClickListener { tableViewModel.getTables() }


        btnAdd.setOnClickListener {
            onAddMenuListener?.onOpenMenuPage(menus, orderResult)
        }

        edtPickupOrder.setOnClickListener {
            DialogDatePicker(requireContext(), object : DialogDatePicker.DateDialogListener {
                override fun onSelectDate(date: String) {
                    edtPickupOrder.setText(date)
                }
            }).show()
        }

        btnPrint.setOnClickListener {
//            if (isOrderValid()) {
            if (isEditMode) orderViewModel.editOrder(getOrderResult()) else orderViewModel.postOrder(
                getOrderResult()
            )
//            }
        }

        edtDiscount.doOnTextChanged { text, _, _, _ ->
            Handler().postDelayed({
                if (!text.isNullOrEmpty()) discount = text.toString().toInt()
                showTotalPrice()
            }, 1000)
        }

        btnPayment.setOnClickListener {
            paymentViewModel.getPayments()
        }

        btnKas.setOnClickListener {
            orderViewModel.getListKas()
        }

        btnCustomerName.setOnClickListener {
            orderViewModel.getListPelanggan()
        }
    }

    private fun onDineInSelected() {
        dineInContainer.visible()
    }

    private fun onPreOrderSelected() {
        preOrderContainer.visible()
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

        orderViewModel.listPelangganLoad.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showPelangganOption(it.data)
                }
                is Load.Loading -> {
                }
            }
        })

        orderViewModel.listKas.observe(this, androidx.lifecycle.Observer {
            when (it) {
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    showKasOption(it.data)
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
                customerName = selectedPelanggan?.naem.orEmpty(),
                customerId = selectedPelanggan?.id ?: 0,
                orderCategory = selectedCategoryOrder ?: KategoriOrder(),
                table = selectedTable ?: Table(),
                discount = discount,
                totalPayment = totalPayment,
                totalPaymentBeforeDiscount = totalPaymentBeforeDiscount,
                wallet = selectedKas ?: Wallet()
            ),
            menu = menuAdapter.data,
            type = selectedOrderNameType,
            paymentMethod = selectedPayment ?: Payment()
        )
    }

    private fun showTotalPrice() {
        totalPaymentBeforeDiscount = menus.map {
            it.totalPrice

        }.sum()
        val totalDiscount = totalPaymentBeforeDiscount.times(discount) / 100
        totalPayment = totalPaymentBeforeDiscount - totalDiscount
        tvTotalPrice.text = toCurrencyFormat(totalPayment)
    }

    private fun showOrderResult() {
        orderResult?.let { order ->
            orderId = order.order.id
            selectedOrderNameType = order.type
            selectedPayment = order.paymentMethod
            selectedPelanggan =
                Customer(id = order.order.customerId, naem = order.order.customerName)
            selectedKas = order.order.wallet
            selectedCategoryOrder = order.order.orderCategory
            selectedTable = order.order.table
            discount = order.order.discount

            btnPayment.text =
                order.paymentMethod.name.ifEmpty { getString(R.string.action_select_payment_method) }
            btnCustomerName.text =
                order.order.customerName.ifEmpty { getString(R.string.action_select_customer) }
            btnKas.text =
                order.order.wallet.name.ifEmpty { getString(R.string.action_select_wallet) }
            btnTableNumber.text =
                order.order.table.number.ifEmpty { getString(R.string.action_select_table) }
            dineInContainer.visibility = if (selectedTable?.number?.isEmpty() == true) View.GONE
            else View.VISIBLE

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

    private fun showPelangganOption(customer: List<Customer>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {
            svPelanggan.visibility = View.VISIBLE
            val pelangganAdapter = PelangganAdapter(
                context = requireContext(),
                datas = customer,
                listener = object : PelangganListener {
                    override fun onPelangganSelected(data: Customer) {
                        selectedPelanggan = data
                        getSelectedPelanggan()
                        dismiss()
                    }
                }
            )

            svPelanggan.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val filter = customer.filter {
                        it.naem.contains(newText.toString())
                    }
                    pelangganAdapter.updateData(filter)
                    return false
                }

            })

            rvOption.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = pelangganAdapter
            }
        }
    }

    private fun showKasOption(payments: List<Wallet>) {
        generateCustomBottomSheetDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_alert_option,
            isCancelable = true,
            isExpandMode = true
        ).apply {

            val kasAdapter = KasAdapter(
                context = requireContext(),
                datas = payments,
                listener = object : KasListener {
                    override fun onKasSelected(data: Wallet) {
                        selectedKas = data
                        getSelectedKas()
                        dismiss()
                    }
                }
            )

            rvOption.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                adapter = kasAdapter
            }
        }
    }


    override fun onDeleteClicked(menu: Menu, position: Int) {
        menus.remove(menu)
        showTotalPrice()
        menuAdapter.updateDataMenu(menus)
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

    fun getSelectedPayment() {
        btnPayment.text = selectedPayment?.name
    }

    fun getSelectedKas() {
        btnKas.text = selectedKas?.name
    }

    fun getSelectedPelanggan() {
        btnCustomerName.text = selectedPelanggan?.naem
    }


}