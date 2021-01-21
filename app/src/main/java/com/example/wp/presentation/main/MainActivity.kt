package com.example.wp.presentation.main

import android.view.View
import android.widget.Button
import com.example.wp.R
import com.example.wp.base.WarungPojokActivity
import com.example.wp.data.preference.SessionManager
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.order.OrderResult
import com.example.wp.presentation.stock.checkstock.CheckStockFragment
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.listener.OpenMenuPageListener
import com.example.wp.presentation.menu.MenusFragment
import com.example.wp.presentation.login.LoginFragment
import com.example.wp.presentation.menu.MenuDetailFragment
import com.example.wp.presentation.menu.MenusContainerFragment
import com.example.wp.presentation.order.OrderFragment
import com.example.wp.presentation.order.OrderListFragment
import com.example.wp.presentation.stock.StockContainerFragment
import com.example.wp.utils.loadFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WarungPojokActivity(), OpenMenuPageListener,
    MenusFragment.OnMenuClickListener,
    MenuListener {

    private var sm: SessionManager? = null

    private val menuFragment: MenusFragment by lazy { MenusFragment() }

    private var selectedMenus = mutableListOf<Menu>()

    override val layoutView: Int = R.layout.activity_main

    override fun onPreparation() {
        sm = SessionManager(this)
    }

    override fun onIntent() {
    }

    override fun onView() {
        if (sm?.isUserLogin() == true) {
            loadFragment(R.id.fl_container, menuFragment)
        } else {
            val loginFragment = LoginFragment()
            loginFragment.onLoginSuccessListener = this
            loadFragment(R.id.fl_container, loginFragment)
        }

        setupOrderButton()
    }

    override fun onAction() {
        menuFragment.onMenuClickListener = this

        tvOrder.setOnClickListener {
            loadFragment(R.id.fl_container, menuFragment)
        }

        tvListOrder.setOnClickListener {
            toOrderListFragment()
        }

        tvMenu.setOnClickListener {
            loadFragment(R.id.fl_container,
                MenusContainerFragment()
            )
        }

        btnOrder.setOnClickListener {
           toOrderFragment(menus = selectedMenus)
        }

        tvCekStokMain.setOnClickListener {
            loadFragment(R.id.fl_container, StockContainerFragment())
        }

    }

    fun toOrderFragment(order:OrderResult?=null,menus:List<Menu>,isEditMode:Boolean=false) {
        val orderFragment = OrderFragment.newInstance(order,menus,isEditMode)
        orderFragment.onAddMenuListener = this
        loadFragment(R.id.fl_container, orderFragment)
    }

    override fun onObserver() {
    }

    override fun onResume() {
        super.onResume()
        setupOrderButton()
    }

    fun toOrderListFragment(){
        loadFragment(R.id.fl_container, OrderListFragment())
    }

    override fun onMenuClicked(menu: Menu) {
        val menuDetailFragment = MenuDetailFragment.newInstance(menu)
        menuDetailFragment.onMenuSelectListener = this
        loadFragment(R.id.fl_container, menuDetailFragment)
    }

    override fun onSelectMenu(menu: Menu) {
        selectedMenus.add(menu)
        setupOrderButton()
    }

    private fun setupOrderButton(){
        orderButton.visibility = if (selectedMenus.isNotEmpty()) View.VISIBLE else View.GONE
        tvQuantityOrder.text = selectedMenus.size.toString()
    }

    override fun onOpenMenuPage() {
        loadFragment(R.id.fl_container, menuFragment)
        setupOrderButton()
    }

    fun getOrderButton(): View {
        return orderButton
    }

    fun clearSelectedMenus(){
        selectedMenus.clear()
    }
}