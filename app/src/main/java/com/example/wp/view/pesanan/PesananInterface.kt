package com.example.wp.view.pesanan

import android.content.Context
import com.example.wp.data.DataItem

interface PesananInterface {
    interface View{
        fun showData(data : List<DataItem>)
        fun alertSuccess(msg : String)
        fun alertFailed(msg : String)
    }

    interface Presenter{
        fun logicData()
        fun initSession(context: Context)

    }
}