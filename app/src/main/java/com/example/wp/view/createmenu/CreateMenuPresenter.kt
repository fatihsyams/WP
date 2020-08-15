package com.example.wp.view.createmenu

import android.content.Context
import android.util.Log
import com.example.wp.data.RequestCreateMenu
import com.example.wp.data.ResponseCreateMenu
import com.example.wp.data.WarungPojokService
import com.example.wp.data.utils.SessionManager
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateMenuPresenter(model: CreateMenuInterface.View) : CreateMenuInterface.Presenter {

    var view: CreateMenuInterface.View? = null

    var sm = SessionManager()

    init {
        view = model
    }

    override fun logicInputMenus(
        name: RequestBody,
        description: RequestBody,
        price: RequestBody,
        category: RequestBody,
        stock: RequestBody,
        image: MultipartBody.Part
    ) {

        val api = WarungPojokService.create(sm)

        if (sm.getBoolean()) {
            api.createMenu(name, description, price, category, stock, image)
                .enqueue(object : Callback<ResponseCreateMenu> {
                    override fun onFailure(call: Call<ResponseCreateMenu>, t: Throwable) {
                        view?.showAlertFailed(t.message.orEmpty())
                        Log.d("failmenu", t.message.orEmpty())
                    }

                    override fun onResponse(
                        call: Call<ResponseCreateMenu>,
                        response: Response<ResponseCreateMenu>
                    ) {

                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            view?.showAlertSuccess(responseBody?.status.toString())
                        }

                    }


                })
        } else {
            Log.d("TOKEN", "tokennya ga ada")
        }

    }


    override fun instencePrefence(context: Context) {
        sm.SessionManager(context)
    }

}