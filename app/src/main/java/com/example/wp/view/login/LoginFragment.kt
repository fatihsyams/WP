package com.example.wp.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.wp.R
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

}