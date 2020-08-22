package com.example.wp.presentation.main

import com.example.wp.R
import com.example.wp.base.WarungPojokActivity
import com.example.wp.data.preference.SessionManager
import com.example.wp.presentation.createmenu.CreateMenuFragment
import com.example.wp.presentation.login.LoginFragment
import com.example.wp.presentation.pesanan.PesananFragment
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : WarungPojokActivity(), LoginFragment.OnLoginSuccessListener {

    private var sm: SessionManager? = null

    override val layoutView: Int = R.layout.activity_main

    override fun onPreparation() {
        sm = SessionManager(this)
    }

    override fun onIntent() {
    }

    override fun onView() {
        if (sm?.isUserLogin() == true) {
            loadFragment(R.id.fl_container, PesananFragment())
        } else {
            val loginFragment = LoginFragment()
            loginFragment.onLoginSuccessListener = this
            loadFragment(R.id.fl_container, loginFragment)
        }

    }

    override fun onAction() {
        tvOrder.setOnClickListener {
            loadFragment(R.id.fl_container, PesananFragment())
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

}