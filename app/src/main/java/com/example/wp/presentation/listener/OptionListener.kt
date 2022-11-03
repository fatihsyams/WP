package com.example.wp.presentation.listener

import com.example.wp.domain.kategoriorder.KategoriOrder
import com.example.wp.domain.menu.TakeAway
import com.example.wp.domain.payment.Payment
import com.example.wp.domain.table.Table

interface TableListener {
    fun onTableSelected(data: Table)
}

interface TakeAwayListener{
    fun onTakeAwaySelected(data:TakeAway)
}


interface PaymentListener{
    fun onPaymentSelected(data:Payment)
}

interface KategoriOrderListener{
    fun onKategoriOrderSelected(data:KategoriOrder)
}