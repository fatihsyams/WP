package com.example.wp.presentation.checkstock

import android.content.Context
import com.example.wp.domain.menu.Menu

interface CheckStockInterface {
    interface View {
        fun showData(data: List<Menu>)
        fun showEror(msg: String)
        fun showSuccess(msg: String)
    }

    interface Presenter {
        fun getDataStock()
        fun updateStock(id : Int, stok : Int)
        fun instencePrefence(context: Context)

    }
}