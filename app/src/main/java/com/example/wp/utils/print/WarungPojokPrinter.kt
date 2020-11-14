package com.example.wp.utils.print

import android.content.Context
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.lib.IPrintToPrinter
import com.example.wp.utils.lib.WoosimPrnMng
import com.example.wp.utils.toCurrencyFormat
import com.woosim.printer.WoosimCmd
import java.text.SimpleDateFormat
import java.util.*

class WarungPojokPrinter(val context: Context, val order:OrderResult, val onPrintFinished: (() -> Unit)? = null) : IPrintToPrinter{
    
    override fun printContent(prnMng: WoosimPrnMng) {
        val format = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss", Locale.US)
        prnMng.apply {
            printStr("Jl. Rambutan raya No. 1D RT 003/001, Kec. Pancoran Mas, Kota Depok", 2, WoosimCmd.ALIGN_CENTER)
            printStr("Tanggal: ${format.format(Date())}", 1, WoosimCmd.ALIGN_CENTER)
            printStr(
                "Customer : ${order.order.customerName}",
                1, WoosimCmd.ALIGN_LEFT
            )
            printStr("================================")
            order.menu.forEach {menu->
                prnMng.printStr("${menu.quantity} ${menu.name}",1, WoosimCmd.ALIGN_LEFT)
                prnMng.printStr(toCurrencyFormat(menu.totalPrice),1, WoosimCmd.ALIGN_RIGHT)
                prnMng.printStr(menu.additionalInformation,1, WoosimCmd.ALIGN_LEFT)
            }
            printStr("--------------------------------", 1, WoosimCmd.ALIGN_LEFT)
            prnMng.printStr(" ITEMS: ${order.menu.size}",1, WoosimCmd.ALIGN_LEFT)
            prnMng.printStr(toCurrencyFormat(order.order.totalPaymentBeforeDiscount),1, WoosimCmd.ALIGN_RIGHT)
            prnMng.printStr(" Discount: ",1, WoosimCmd.ALIGN_LEFT)
            prnMng.printStr("${order.order.discount} %",1, WoosimCmd.ALIGN_RIGHT)
            prnMng.printStr(" Total : ",1, WoosimCmd.ALIGN_LEFT)
            prnMng.printStr(toCurrencyFormat(order.order.totalPayment),1, WoosimCmd.ALIGN_RIGHT)
            printNewLine()
            printStr("================================")
            printStr(printClosingMessage(order.type), 1, WoosimCmd.ALIGN_CENTER)
        }
        printEnded(prnMng)
    }

    override fun printEnded(prnMng: WoosimPrnMng?) {
        onPrintFinished?.invoke()
    }

    private fun printClosingMessage(orderType:Int):String{
        return when(orderType){
            OrderTypeEnum.DINE_IN.type -> "po message"
            OrderTypeEnum.TAKE_AWAY.type -> "take away message"
            OrderTypeEnum.PRE_ORDER.type -> "po message"
            else -> "po message"
        }
    }
}