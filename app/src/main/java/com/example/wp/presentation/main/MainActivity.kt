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

class MainActivity : AppCompatActivity() {

    private var sm = SessionManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm.initSessionManager(this)

        if (sm.isUserLogin()){
            loadFragment(R.id.fl_container, PesananFragment())
        }else{
            loadFragment(R.id.fl_container, LoginFragment())
        }

        tvInputMenuMain.setOnClickListener {
            loadFragment(R.id.fl_container, CreateMenuFragment())
        }
    }

}