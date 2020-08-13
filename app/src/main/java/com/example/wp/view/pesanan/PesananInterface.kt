package com.example.wp.view.pesanan

import com.example.wp.data.DataItem

interface PesananInterface {
    interface View{
        fun showData(data : List<DataItem>)
        fun alertSuccess(msg : String)
        fun alertFailed(msg : String)
    }

    interface Presenter{
        fun logicData()
    }
}