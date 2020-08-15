package com.example.wp.view.pesanan

import android.content.Context
import android.util.Log
import com.example.wp.data.ResponseMenuWp
import com.example.wp.data.WarungPojokService
import com.example.wp.data.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananPresenter(model: PesananInterface.View) :
    PesananInterface.Presenter {

    var view: PesananInterface.View? = null
    var sm = SessionManager()

    init {
        view = model
    }


    override fun logicData() {
        val service = WarungPojokService.create(sm)
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
                    if (sm.getBoolean()) {
                        view?.alertSuccess("${responseBody?.message}")
                        view?.showData(responseBody?.data.orEmpty())
                        Log.d("BISA", "${responseBody?.message}")

                    } else {
                        view?.alertFailed("${responseBody?.message}")
                        Log.d("GAGALLL", "${responseBody?.message}")

                    }
                    }

            }

        })
    }

    override fun initSession(context: Context) {
        sm.SessionManager(context)
    }

//    override fun checkToken() {
//        if (sm.getBoolean()) {
//            view?.showData()
//        }
//    }

}