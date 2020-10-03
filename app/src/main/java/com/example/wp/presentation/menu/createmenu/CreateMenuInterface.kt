package com.example.wp.presentation.menu.createmenu

import android.content.Context
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
            image: File,
            grabFoodPrice:String,
            goFoodPrice:String
        )
        fun instencePrefence(context: Context)

        fun updateMenus(
            id : Int,
            name: String,
            description: String,
            price: String,
            category: String,
            stock: String,
            image: File
        )
    }
}