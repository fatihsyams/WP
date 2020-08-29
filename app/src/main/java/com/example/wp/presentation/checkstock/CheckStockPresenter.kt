package com.example.wp.presentation.checkstock

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.wp.data.api.model.response.ResponseMenuWp
import com.example.wp.data.preference.SessionManager
import com.example.wp.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckStockPresenter(model: CheckStockInterface.View) : CheckStockInterface.Presenter {

    var view: CheckStockInterface.View? = null
    var sm: SessionManager? = null

    init {
        view = model
    }

    override fun getDataStock() {
        sm?.let { sm ->
            val api = NetworkUtils.create(sm)
            api.getMenuMVP().enqueue(object : Callback<ResponseMenuWp> {
                override fun onFailure(call: Call<ResponseMenuWp>, t: Throwable) {
                    Log.d("stok", "$t.message")
                    view?.showEror("${t.message}")
                }

                override fun onResponse(
                    call: Call<ResponseMenuWp>,
                    response: Response<ResponseMenuWp>
                ) {
                    if (response.isSuccessful) {
                        var responBody = response.body()
                        view?.showData(responBody?.data.orEmpty())
                        Log.d("stok", response.message())
                    } else {
                        Log.d("stok", "${response.errorBody()}")
                    }
                }

            })
        }
    }

    override fun instencePrefence(context: Context) {
        sm = SessionManager(context)
    }


}