package com.example.wp.presentation.createmenu

import android.content.Context
import android.util.Log
import com.example.wp.data.api.model.response.ResponseCreateMenu
import com.example.wp.data.api.model.response.ResponseUpdateData
import com.example.wp.data.preference.SessionManager
import com.example.wp.utils.NetworkUtils.Companion.create
import com.example.wp.utils.toRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateMenuPresenter(model: CreateMenuInterface.View) : CreateMenuInterface.Presenter {

    var view: CreateMenuInterface.View? = null

    var sm: SessionManager? = null

    init {
        view = model
    }

    override fun logicInputMenus(
        name: String,
        description: String,
        price: String,
        category: String,
        stock: String,
        image: File
    ) {

        sm?.let { sm ->
            val api = create(sm)
            if (sm.isUserLogin()) {
                api.createMenu(
                    name = name.toRequestBody(),
                    description = description.toRequestBody(),
                    price = price.toRequestBody(),
                    category_menu_id = category.toRequestBody(),
                    stock = stock.toRequestBody(),
                    image = MultipartBody.Part.createFormData(
                        "image",
                        image.name,
                        image.toRequestBody()
                    )
                )
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
                            } else {
                                view?.showAlertFailed("error ${response.message()}")
                            }

                        }
                    })
            } else {
                Log.d("TOKEN", "tokennya ga ada")
            }
        }
    }

    override fun instencePrefence(context: Context) {
        sm = SessionManager(context)
    }

    override fun updateMenus(
        id: Int,
        name: String,
        description: String,
        price: String,
        category: String,
        stock: String,
        image: File
    ) {
        sm?.let { sm ->
            val api = create(sm)
            if (sm.isUserLogin()) {
                api.updateMenu(
                    id = id,
                    name = name.toRequestBody(),
                    description = description.toRequestBody(),
                    price = price.toRequestBody(),
                    category_menu_id = category.toRequestBody(),
                    stock = stock.toRequestBody(),
                    image = MultipartBody.Part.createFormData(
                        "image",
                        image.name,
                        image.toRequestBody()
                    )
                )
                    .enqueue(object : Callback<ResponseUpdateData> {
                        override fun onFailure(call: Call<ResponseUpdateData>, t: Throwable) {
                            view?.showAlertFailed(t.message.orEmpty())
                            Log.d("failupdatemenu", t.message.orEmpty())
                        }

                        override fun onResponse(
                            call: Call<ResponseUpdateData>,
                            response: Response<ResponseUpdateData>
                        ) {

                            if (response.isSuccessful) {
                                val responseBody = response.body()
                                view?.showAlertSuccess(responseBody?.status.toString())
                            } else {
                                view?.showAlertFailed("error ${response.message()}")
                            }

                        }
                    })
            } else {
                Log.d("TOKEN", "tokennya ga ada")
            }
        }
    }


}