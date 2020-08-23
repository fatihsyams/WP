package com.example.wp.presentation.listmenu

import android.content.Context
import com.example.wp.data.api.model.response.DataItem

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