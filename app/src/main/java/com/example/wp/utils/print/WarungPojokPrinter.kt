package com.example.wp.utils.print

import android.content.Context
import android.util.Log
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.lib.IPrintToPrinter
import com.example.wp.utils.lib.WoosimPrnMng
import com.example.wp.utils.lib.printerWordMng
import com.example.wp.utils.printerFactory
import com.example.wp.utils.toCurrencyFormat
import com.woosim.printer.WoosimCmd
import org.apache.http.util.ByteArrayBuffer
import java.text.SimpleDateFormat
import java.util.*

class WarungPojokPrinter(
    val context: Context,
    val order: OrderResult,
    val onPrintFinished: (() -> Unit)? = null
) : IPrintToPrinter{
    
    override fun printContent(prnMng: WoosimPrnMng) {
        Log.d("PRINT", "printing..")
        val wordMng: printerWordMng = printerFactory.createPaperMng(context)
        val format = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss", Locale.US)
        prnMng.apply {
            printStr("Jl. Rambutan raya No. 1D RT 003/001, Kec. Pancoran Mas, Kota Depok", 1, WoosimCmd.ALIGN_CENTER)
            printStr("Tanggal: ${format.format(Date())}", 1, WoosimCmd.ALIGN_CENTER)
            printStr(
                "Customer : ${order.order.customerName}",
                1, WoosimCmd.ALIGN_LEFT
            )
            printStr("================================")
            order.menu.forEach { menu->
                printStr(wordMng.autoWordWrap("${menu.quantity} ${menu.name} \t ${toCurrencyFormat(menu.totalPrice)}"), 1, WoosimCmd.ALIGN_LEFT)
//                printStr(toCurrencyFormat(menu.totalPrice), 1, WoosimCmd.ALIGN_RIGHT)
                printStr(menu.additionalInformation, 1, WoosimCmd.ALIGN_LEFT)
            }
            printStr("--------------------------------", 1, WoosimCmd.ALIGN_LEFT)
            printStr(" ITEMS: ${order.menu.size}", 1, WoosimCmd.ALIGN_LEFT)
            printStr(
                toCurrencyFormat(order.order.totalPaymentBeforeDiscount),
                1,
                WoosimCmd.ALIGN_RIGHT
            )
            printStr(" Discount: ", 1, WoosimCmd.ALIGN_LEFT)
            printStr("${order.order.discount} %", 1, WoosimCmd.ALIGN_RIGHT)
            printStr(" Total : ", 1, WoosimCmd.ALIGN_LEFT)
            printStr(toCurrencyFormat(order.order.totalPayment), 1, WoosimCmd.ALIGN_RIGHT)
            printNewLine()
            printStr("================================")
            printStr(printClosingMessage(order.type), 1, WoosimCmd.ALIGN_CENTER)
        }
        printEnded(prnMng)
    }

    override fun printEnded(prnMng: WoosimPrnMng?) {
        Log.d("PRINT", "finish print")
        onPrintFinished?.invoke()
    }

    private fun printClosingMessage(orderType: Int):String{
        return when(orderType){
            OrderTypeEnum.DINE_IN.type -> "Terimakasih Atas Kunjungannya"
            OrderTypeEnum.TAKE_AWAY.type -> "Terimakasih Atas Orderannya"
            OrderTypeEnum.PRE_ORDER.type -> "Selamat Menikmati"
            else -> "Selamat Menikmati"
        }
    }
}