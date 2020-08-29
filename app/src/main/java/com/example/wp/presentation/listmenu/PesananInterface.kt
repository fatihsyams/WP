package com.example.wp.presentation.listmenu

import android.content.Context
import com.example.wp.domain.menu.Menu

interface PesananInterface {
    interface View{
        fun showMenus(data : List<Menu>)
        fun alertSuccess(msg : String)
        fun alertFailed(msg : String)
    }

    interface Presenter{
        fun logicData()
        fun initSession(context: Context)

    }
}