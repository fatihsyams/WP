package com.example.wp.presentation.main

import android.view.View
import com.example.wp.R
import com.example.wp.base.WarungPojokActivity
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.data.preference.SessionManager
import com.example.wp.presentation.createmenu.CreateMenuFragment
import com.example.wp.presentation.listener.MenuListener
import com.example.wp.presentation.login.LoginFragment
import com.example.wp.presentation.listmenu.PesananFragment
import com.example.wp.presentation.menu.MenuDetailFragment
import com.example.wp.presentation.order.OrderFragment
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WarungPojokActivity(), LoginFragment.OnLoginSuccessListener, PesananFragment.OnMenuClickListener,
MenuListener{

    private var sm: SessionManager? = null

    private val menuFragment:PesananFragment by lazy { PesananFragment() }

    private var selectedMenus = mutableListOf<DataItem>()

    override val layoutView: Int = R.layout.activity_main

    override fun onPreparation() {
        sm = SessionManager(this)
    }

    override fun onIntent() {
    }

    override fun onView() {
        if (sm?.isUserLogin() == true) {
            menuFragment.onMenuClickListener = this
            loadFragment(R.id.fl_container, menuFragment)
        } else {
            val loginFragment = LoginFragment()
            loginFragment.onLoginSuccessListener = this
            loadFragment(R.id.fl_container, loginFragment)
        }
    }

    override fun onAction() {
        tvOrder.setOnClickListener {
            loadFragment(R.id.fl_container, menuFragment)
        }

        tvMenu.setOnClickListener {
            loadFragment(R.id.fl_container, CreateMenuFragment())
        }

        btnOrder.setOnClickListener {
            loadFragment(R.id.fl_container, OrderFragment.newInstance(selectedMenus))
        }
    }

    override fun onObserver() {
    }

    override fun moveToHomeFragment() {
        loadFragment(R.id.fl_container, PesananFragment())
    }

    override fun onMenuClicked(menu: DataItem) {
        val menuDetailFragment = MenuDetailFragment.newInstance(menu)
        menuDetailFragment.onMenuSelectListener = this
        loadFragment(R.id.fl_container, menuDetailFragment)
    }

    override fun onSelectMenu(menu: DataItem) {
        selectedMenus.add(menu)
        setupOrderButton()
    }

    private fun setupOrderButton(){
        btnOrder.visibility = if (selectedMenus.isNotEmpty()) View.VISIBLE else View.GONE
    }
}