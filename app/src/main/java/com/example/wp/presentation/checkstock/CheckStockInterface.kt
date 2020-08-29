package com.example.wp.presentation.checkstock

import android.content.Context
import com.example.wp.data.api.model.response.DataItem
import com.example.wp.data.api.model.response.ResponseMenuWp

interface CheckStockInterface {
    interface View {
        fun showData(data: List<DataItem>)
        fun showEror(msg : String)
    }

    interface Presenter {
        fun getDataStock()
        fun instencePrefence(context: Context)

    }
}