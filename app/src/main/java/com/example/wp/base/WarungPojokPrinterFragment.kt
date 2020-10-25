package com.example.wp.base

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bidikan.baseapp.ui.WarungPojokFragment
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.DeviceConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.exceptions.EscPosEncodingException
import com.dantsu.escposprinter.exceptions.EscPosParserException
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.emptyString
import com.example.wp.utils.toCurrencyFormat
import java.text.SimpleDateFormat
import java.util.*

const val PERMISSION_BLUETOOTH = 1

abstract class WarungPojokPrinterFragment : WarungPojokFragment() {

    abstract val order: OrderResult

    fun printBluetooth( onPrintFinished:(()->Unit)? = null) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.BLUETOOTH),
                PERMISSION_BLUETOOTH
            )
        } else {
            printIt(BluetoothPrintersConnections.selectFirstPaired(), onPrintFinished)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                PERMISSION_BLUETOOTH -> printBluetooth()
            }
        }
    }

    /*==============================================================================================
    ===================================ESC/POS PRINTER PART=========================================
    ==============================================================================================*/
    open fun  printIt(printerConnection: DeviceConnection?, onPrintFinished:(()->Unit)? = null) {
        try {
            val format = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss")
            val printer = EscPosPrinter(printerConnection, 203, 48f, 32)
            printer
                .printFormattedText(
                    "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(
                        printer,
                        context?.resources
                            ?.getDrawableForDensity(
                                R.drawable.logowp,
                                DisplayMetrics.DENSITY_MEDIUM
                            )
                    ) + "</img>\n" +
                            "[L]\n" +
                            "[C]<font size='small'>Jl. Rambutan 1-7, Depok Jaya,</font>\n" +
                            "[C]<font size='small'>Kec. Pancoran Mas, Kota Depok,</font>\n" +
                            "[C]<font size='small'>Jawa Barat 16342</font>\n" +
                            "[L]\n" +
                            "[C]<font size='small'> Tanggal: " + format.format(Date()) + "</font>\n" +
                            "[L]\n" +
                            "[C]<font size='small'> Customer : " + order.order.customerName + "</font>\n" +
                            "[C]================================\n" +
                            "[L]\n" +
                            "[L]<font size='small'> " + printMenus(order.menu) + "</font>\n" +
                            "[C]--------------------------------\n" +
                            "[L]ITEMS: ${order.menu.size}[R]${toCurrencyFormat(order.order.totalPaymentBeforeDiscount)}\n" +
                            "[L]Discount :[R]${order.order.discount} %\n" +
                            "[L]Total :[R]${toCurrencyFormat(order.order.totalPayment)}\n" +
                            "[L]\n" +
                            "[C]================================\n" +
                            "[L]\n" +
                            "[C]Terima Kasih\n" +
                            "[C]Atas Kunjungan Anda\n"
                )

            val handler = Handler()
            val delayTime = order.menu.size * 3000L
            handler.postDelayed({
                onPrintFinished?.invoke()
            }, delayTime)
        } catch (e: EscPosConnectionException) {
            e.printStackTrace()
            Log.d("ERROR PRINT", e.message)
            AlertDialog.Builder(requireContext())
                .setTitle("Broken connection")
                .setMessage(e.message)
                .show()
        } catch (e: EscPosParserException) {
            e.printStackTrace()
            Log.d("ERROR PRINT", e.message)
            AlertDialog.Builder(requireContext())
                .setTitle("Invalid formatted text")
                .setMessage(e.message)
                .show()
        } catch (e: EscPosEncodingException) {
            e.printStackTrace()
            Log.d("ERROR PRINT", e.message)
            AlertDialog.Builder(requireContext())
                .setTitle("Bad selected encoding")
                .setMessage(e.message)
                .show()
        } catch (e: EscPosBarcodeException) {
            e.printStackTrace()
            Log.d("ERROR PRINT", e.message)
            AlertDialog.Builder(requireContext())
                .setTitle("Invalid barcode")
                .setMessage(e.message)
                .show()
        }
    }

    private fun printMenus(menus:List<Menu>):String{
        var menuPrint = emptyString()
        menus.forEach {menu->
            menuPrint = menuPrint + "${menu.quantity} ${menu.name} [R]${toCurrencyFormat(menu.price)}\n" +
                    "[L] ${menu.additionalInformation}\n"
        }
        return menuPrint
    }

}