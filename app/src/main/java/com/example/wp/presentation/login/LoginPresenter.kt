package com.example.wp.presentation.login

import android.content.Context
import android.util.Log
import com.example.wp.data.api.model.request.RequestLogin
import com.example.wp.data.api.model.response.ResponseLoginn
import com.example.wp.data.preference.SessionManager
import com.example.wp.utils.NetworkUtils.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(model: LoginInterface.View) : LoginInterface.Presenter {

    var view: LoginInterface.View? = null
    var sm : SessionManager? = null

    init {
        view = model
    }

    override fun doLogin(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            view?.showLoginFailed("Isi data anda")
        }

        sm?.let { sm->
            val api = create(sm)
            val loginBody = RequestLogin(
                username,
                password
            )
            api.login(loginBody).enqueue(object : Callback<ResponseLoginn> {
                override fun onFailure(call: Call<ResponseLoginn>, t: Throwable) {
                    view?.showLoginFailed(t.message.toString())
                    Log.d("GagalLogin", t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseLoginn>,
                    response: Response<ResponseLoginn>
                ) {
                    if (response.body() != null) {
                        var responseBody = response.body()
                        view?.showLoginSuccess("Selamat Datang" + "${responseBody?.token}")
                        if (responseBody?.token != null) {
                            sm.saveUserLogin(true)
                            sm.saveToken(responseBody?.token.toString())
                            view?.moveHome()
                        } else {
                            view?.showLoginFailed("Token Tidak Dapat")
                            Log.d("TOKEN", "Token tidak dapat")
                        }
                    } else {
                        Log.d("Gagal response", "response gagal")
                    }
                }
            })

        }
    }

    override fun checkLogin() {
        if (sm?.isUserLogin() == true) {
            view?.moveHome()
        } else {
            view?.showLoginFailed("Token Tidak Dapat")
        }
    }

    override fun instencePrefence(context: Context) {
        sm = SessionManager(context)
    }

}