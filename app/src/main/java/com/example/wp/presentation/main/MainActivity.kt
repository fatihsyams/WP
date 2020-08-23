package com.example.wp.presentation.main

import com.example.wp.R
import com.example.wp.base.WarungPojokActivity
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.data.preference.SessionManager
import com.example.wp.presentation.createmenu.CreateMenuFragment
import com.example.wp.presentation.login.LoginFragment
import com.example.wp.presentation.listmenu.PesananFragment
import com.example.wp.presentation.menu.MenuDetailFragment
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WarungPojokActivity(), LoginFragment.OnLoginSuccessListener, PesananFragment.OnMenuClickListener {

    private var sm: SessionManager? = null

    private val menuFragment:PesananFragment by lazy { PesananFragment() }

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
    }

    override fun onObserver() {
    }

    override fun moveToHomeFragment() {
        loadFragment(R.id.fl_container, PesananFragment())
    }

    override fun onMenuClicked(menu: DataItem) {
        loadFragment(R.id.fl_container, MenuDetailFragment.newInstance(menu))
    }

}