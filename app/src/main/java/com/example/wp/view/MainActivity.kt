package com.example.wp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wp.view.pesanan.PesananFragment
import com.example.wp.R
import com.example.wp.view.login.LoginFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     loadFragment(LoginFragment())

    }
    private fun loadFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}