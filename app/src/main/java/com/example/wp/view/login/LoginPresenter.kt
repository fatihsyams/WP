package com.example.wp.view.login

import android.content.Context
import android.util.Log
import com.example.wp.data.RequestLogin
import com.example.wp.data.ResponseLogin
import com.example.wp.data.ResponseLoginn
import com.example.wp.data.WarungPojokService
import com.example.wp.data.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(model: LoginInterface.View) : LoginInterface.Presenter {

    var view: LoginInterface.View? = null

    init {
        view = model
    }

    var sm = SessionManager()
    override fun doLogin(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            view?.showLoginFailed("Isi data anda")
        }

        val api = WarungPojokService.create()
        val loginBody = RequestLogin(username, password)
        api.login(loginBody).enqueue(object : Callback<ResponseLoginn> {
            override fun onFailure(call: Call<ResponseLoginn>, t: Throwable) {
                view?.showLoginFailed(t.message.toString())
                Log.d("GagalLogin", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseLoginn>, response: Response<ResponseLoginn>) {
                if (response.body() != null) {
                    var responseBody = response.body()
                    view?.showLoginSuccess("Selamat Datang")
                    if (responseBody?.token != null) {
                        sm.saveBoolean(true)
                    } else {
                        Log.d("TOKEN", "Token tidak dapat")
                    }
                } else {
                    Log.d("Gagal response", "response gagal")
                }
            }
        })


    }

    override fun checkLogin() {
        if (sm.getBoolean()) {
            view?.showLoginSuccess("Selamat Datang")
        }
    }

    override fun instencePrefence(context: Context) {
        sm.SessionManager(context)
    }

}