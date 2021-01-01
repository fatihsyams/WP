package com.example.wp.presentation.order

import android.app.ProgressDialog
import android.os.Handler
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wp.R
import com.example.wp.base.WarungPojokPrinterFragment
import com.example.wp.domain.order.OrderResult
import com.example.wp.presentation.adapter.OrderResultAdapter
import com.example.wp.presentation.listener.OrderResultListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.presentation.viewmodel.OrderViewModel
import com.example.wp.utils.*
import com.example.wp.utils.enum.OrderStatusTypeEnum
import kotlinx.android.synthetic.main.fragment_order_list.*
import kotlinx.android.synthetic.main.layout_alert_option.tvTitle
import kotlinx.android.synthetic.main.layout_print_dialog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderListFragment : WarungPojokPrinterFragment(), OrderResultListener {

    private val orderViewModel: OrderViewModel by viewModel()

    override var order: OrderResult = OrderResult()

    private var isCanceled = false

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }

    override val layoutView: Int = R.layout.fragment_order_list

    override fun onPreparation() {
        progressDialog.setCancelable(false)
    }

    override fun onIntent() {
    }

    override fun onView() {
    }

    override fun onAction() {
    }

    override fun onObserver() {
        orderViewModel.getOrders()
        orderViewModel.ordersLoad.observe(this, Observer {
            when (it) {
                is Load.Loading -> {
                    msvOrders.showLoadingView()
                }
                is Load.Fail -> {
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    msvOrders.showContentView()
                    showOrders(it.data)
                    if (it.data.isEmpty()) {
                        showToast("Tidak ada order")
                    }
                }
            }
        })

        orderViewModel.updateOrderStatus.observe(this, Observer {
            when (it) {
                is Load.Loading -> {
                    progressDialog.show()
                }
                is Load.Fail -> {
                    progressDialog.dismiss()
                    showToast(it.error.localizedMessage)
                }
                is Load.Success -> {
                    progressDialog.dismiss()
                    if (isCanceled){
                        orderViewModel.getOrders()
                    }else{
                        printStruk()
                    }
                }
            }
        })
    }

    private fun showOrders(datas: List<OrderResult>) {
        val orderAdapter = OrderResultAdapter(requireContext(), datas, this)
        rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderAdapter
        }
    }

    override fun onBillClicked(orderResult: OrderResult) {
        order = orderResult
        printStruk()
    }

    override fun onPayClicked(orderResult: OrderResult) {
        isCanceled = false
        order = orderResult
        orderViewModel.updateOrderStatus(
            orderId = orderResult.order.id.toString(),
            status = OrderStatusTypeEnum.PAY.status
        )
    }

    override fun onCancelClicked(orderResult: OrderResult) {
        isCanceled = true
        order = orderResult
        orderViewModel.updateOrderStatus(
            orderId = orderResult.order.id.toString(),
            status = OrderStatusTypeEnum.CANCEL.status
        )
    }

    override fun onOrderClicked(order: OrderResult) {
        (activity as MainActivity).toOrderFragment(order,order.menu)
    }

    private fun printStruk() {
        progressDialog.show()
        println("ORDER STRUK $order")
        printBluetooth(onPrintFinished =  {
            showPrintAlert()
        },
        onErrorOccured = {message->
            showToast(message)
            progressDialog.dismiss()
        })
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
                val handler = Handler()
                val delayTime = 5000L
                handler.postDelayed({
                    Log.d("PRINT", "preparing print again...")
                    printStruk()
                }, delayTime)
            }
        }
    }

    private fun onPrintFinish() {
        if (progressDialog.isShowing) progressDialog.dismiss()
        orderViewModel.getOrders()
    }

}