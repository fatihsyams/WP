package com.example.wp.presentation.createmenu

import android.content.Context
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface CreateMenuInterface {
    interface View {
        fun showAlertSuccess(msg: String)
        fun showAlertFailed(msg: String)
    }

    interface Presenter {
        fun logicInputMenus(
            name: String,
            description: String,
            price: String,
            category: String,
            stock: String,
            image: File
        )
        fun instencePrefence(context: Context)
    }
}
