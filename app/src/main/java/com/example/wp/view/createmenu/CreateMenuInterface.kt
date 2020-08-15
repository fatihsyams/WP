package com.example.wp.view.createmenu

import android.content.Context
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface CreateMenuInterface {
    interface View {
        fun showAlertSuccess(msg: String)
        fun showAlertFailed(msg: String)
    }

    interface Presenter {
        fun logicInputMenus(
            name: RequestBody,
            description: RequestBody,
            price: RequestBody,
            category: RequestBody,
            stock: RequestBody,
            image: MultipartBody.Part
        )
        fun instencePrefence(context: Context)
    }
}
