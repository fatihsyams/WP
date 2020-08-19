package com.example.wp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wp.R
import com.example.wp.data.preference.SessionManager
import com.example.wp.presentation.createmenu.CreateMenuFragment
import com.example.wp.presentation.login.LoginFragment
import com.example.wp.presentation.pesanan.PesananFragment
import com.example.wp.utils.loadFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), LoginFragment.OnLoginSuccessListener {

    private var sm = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm.initSessionManager(this)

        if (sm.isUserLogin()){
            loadFragment(R.id.fl_container, PesananFragment())
        }else{
            val loginFragment = LoginFragment()
            loginFragment.onLoginSuccessListener = this
            loadFragment(R.id.fl_container, loginFragment)
        }

        tvInputMenuMain.setOnClickListener {
            loadFragment(R.id.fl_container, CreateMenuFragment())
        }
    }

    override fun moveToHomeFragment() {
        loadFragment(R.id.fl_container, PesananFragment())
    }

}