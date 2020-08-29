package com.example.wp.presentation.listmenu

import android.content.Context
import android.util.Log
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.data.preference.SessionManager
import com.example.wp.utils.NetworkUtils.Companion.create
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananPresenter(model: PesananInterface.View) :
    PesananInterface.Presenter {

    var view: PesananInterface.View? = null
    var sm : SessionManager? = null

    init {
        view = model
    }

    override fun logicData() {
        sm?.let { sm->
            val service = create(sm)
            service.getMenuMVP().enqueue(object : Callback<ResponseMenuWp> {
                override fun onFailure(call: Call<ResponseMenuWp>, t: Throwable) {
                    Log.d("GAGAL", t.message.toString())
                }

                override fun onResponse(
                    call: Call<ResponseMenuWp>,
                    response: Response<ResponseMenuWp>
                ) {
                    val responseBody = response.body()

                    if (response.isSuccessful) {
                        if (sm.isUserLogin()) {
                            view?.alertSuccess("${responseBody?.message}")
//                            view?.showMenus(responseBody?.data.orEmpty())
                            Log.d("BISA", "${responseBody?.message}")

                        } else {
                            view?.alertFailed("${responseBody?.message}")
                            Log.d("GAGALLL", "${responseBody?.message}")

                        }
                    }

                }

            })
        }
    }

    override fun initSession(context: Context) {
        sm = SessionManager(context)
    }

}