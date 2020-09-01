package com.example.wp.presentation.order

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.menu.Table
import com.example.wp.domain.menu.TakeAway
import com.example.wp.presentation.adapter.MenusAdapter
import com.example.wp.presentation.adapter.MenusAdapter.Companion.ORDER_TYPE
import com.example.wp.presentation.adapter.TableAdapter
import com.example.wp.presentation.adapter.TakeAwayAdapter
import com.example.wp.presentation.listener.CalculateMenuListener
import com.example.wp.presentation.listener.OpenMenuPageListener
import com.example.wp.presentation.listener.TableListener
import com.example.wp.presentation.listener.TakeAwayListener
import com.example.wp.presentation.main.MainActivity
import com.example.wp.utils.*
import com.example.wp.utils.constants.AppConstants
import com.example.wp.utils.datePicker.DialogDatePicker
import com.example.wp.utils.enum.OrderTypeEnum
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_alert_option.*
import java.util.*

class OrderFragment : WarungPojokFragment(), CalculateMenuListener {

    companion object {
        @JvmStatic
        fun newInstance(menus: List<Menu>) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(AppConstants.KEY_MENU, menus as ArrayList<Menu>)
                }
            }
    }

    private var menus = mutableListOf<Menu>()

    var onAddMenuListener: OpenMenuPageListener? = null

    private var selectedOrderType = 0

    private var selectedTable:Table? = null

    private var selectedTakeAway:TakeAway? = null

    override val layoutView: Int = R.layout.fragment_order

    override fun onPreparation() {
    }

    override fun onIntent() {
        menus =
            arguments?.getParcelableArrayList<Menu>(AppConstants.KEY_MENU) ?: mutableListOf()
    }

    override fun onView() {
        (activity as MainActivity).getOrderButton().gone()
        showMenus()
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
        }

        btnTableNumber.setOnClickListener { showTableOptions() }

        btnTakeAwayType.setOnClickListener { showTakeAwayOptions() }

        btnAdd.setOnClickListener {
            onAddMenuListener?.onOpenMenuPage()
        }

        edtPickupOrder.setOnClickListener {
          DialogDatePicker(requireContext(), object :DialogDatePicker.DateDialogListener{
              override fun onSelectDate(date: String) {
                  edtPickupOrder.setText(date)
              }
          }).show()
        }

        btnPrint.setOnClickListener { showToast("print") }
    }

    override fun onObserver() {

    }

    private fun showMenus() {

        val menuAdapter = MenusAdapter(
            context = requireContext(),
            data = menus,
            type = ORDER_TYPE,
            onCalculateMenuListener = this
        )

        rvOrders.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }

    }

    private fun showTableOptions() {
        val tables = mutableListOf<Table>()
        for (i in 0 until 9) {
            tables.add(Table(i.toString()))
        }

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
    }

    fun getSelectedTable() {
        btnTableNumber.text = selectedTable?.number.orEmpty()
    }

    fun getSelectedTakeAway() {
        btnTakeAwayType.text = selectedTakeAway?.name.orEmpty()
    }

}