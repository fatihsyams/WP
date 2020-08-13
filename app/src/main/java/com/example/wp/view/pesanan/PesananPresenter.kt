package com.example.wp.view.pesanan

import android.util.Log
import com.example.wp.data.ResponseMenuWp
import com.example.wp.data.WarungPojokService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PesananPresenter(model: PesananInterface.View) :
    PesananInterface.Presenter {

    var view: PesananInterface.View? = null

    init {
        view = model
    }

    override fun logicData() {
        val service = WarungPojokService.create()
        service.getMenuMVP().enqueue(object : Callback<ResponseMenuWp> {
            override fun onFailure(call: Call<ResponseMenuWp>, t: Throwable) {
                Log.d("GAGAL", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<ResponseMenuWp>,
                response: Response<ResponseMenuWp>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    view?.alertSuccess("${responseBody?.message}")
                    view?.showData(responseBody?.data.orEmpty())
                    Log.d("BISA", "${responseBody?.message}")

                } else {
                    view?.alertFailed("${responseBody?.message}")
                    Log.d("GAGAL", "${responseBody?.message}")

                }
            }

        })
    }

}