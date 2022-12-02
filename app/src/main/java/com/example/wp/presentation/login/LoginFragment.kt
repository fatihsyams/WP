package com.example.wp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wp.R
import com.example.wp.domain.order.OrderResult
import com.example.wp.presentation.listener.OpenMenuPageListener
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment(), LoginInterface.View {

    lateinit var presenter: LoginPresenter

    var onLoginSuccessListener:OpenMenuPageListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()

        btnLogin.setOnClickListener {
            presenter.doLogin(
                edtUsernameLogin.text.toString(),
                edtPasswordLogin.text.toString()
            )
        }
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
       onLoginSuccessListener?.onOpenMenuPage(listOf())
    }
}