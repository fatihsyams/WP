package com.example.wp.presentation.listener

import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.table.Table

interface TableListener {
    fun onTableSelected(data: Table)
}

interface TakeAwayListener{
    fun onTakeAwaySelected(data:TakeAway)
}