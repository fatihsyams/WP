package com.example.wp.view.login

import android.content.Context

interface LoginInterface {
    interface View {
        fun showLoginSuccess(msg: String)
        fun showLoginFailed(msg: String)
        fun moveHome()
    }

    interface Presenter {
        fun doLogin(
            username: String,
            password: String
        )
        fun checkLogin()
        fun instencePrefence(context: Context)
    }
}