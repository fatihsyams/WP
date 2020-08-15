package com.example.wp.view.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.wp.R
import com.example.wp.view.pesanan.PesananFragment
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), LoginInterface.View {


    lateinit var presenter: LoginPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            presenter.doLogin(
                edtUsernameLogin.text.toString(),
                edtPasswordLogin.text.toString()
            )
        }
        initPresenter()

    }


    private fun initPresenter() {
        presenter = LoginPresenter(this)
        presenter.instencePrefence(context!!)
        presenter.checkLogin()

    }

    override fun showLoginSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

    }

    override fun showLoginFailed(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

    }

    override fun moveHome() {
        val fragment: Fragment = PesananFragment()
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

//    private fun loadFragment(fragment : Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fl_container, fragment)
//            .commit()
//    }

}