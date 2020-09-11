package com.example.wp.presentation.listener

import com.example.wp.domain.menu.Table
import com.example.wp.domain.menu.TakeAway

interface TableListener {
    fun onTableSelected(data:Table)
}

interface TakeAwayListener{
    fun onTakeAwaySelected(data:TakeAway)
}