package com.example.wp.base

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.util.Log
import android.widget.ArrayAdapter
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
import com.example.wp.R
import com.example.wp.domain.menu.Menu
import com.example.wp.domain.order.OrderResult
import com.example.wp.utils.*
import com.example.wp.utils.enum.OrderTypeEnum
import com.example.wp.utils.lib.WoosimPrnMng
import com.example.wp.utils.print.WarungPojokPrinter
import kotlinx.android.synthetic.main.layout_bluetooth_list_dialog.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val PERMISSION_BLUETOOTH = 1

abstract class WarungPojokPrinterFragment : WarungPojokFragment() {

    abstract val order: OrderResult

    private val mBtAdapter: BluetoothAdapter? = null
    private val mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null
    private val mNewDevicesArrayAdapter: ArrayAdapter<String>? = null

    private var mPrnMng: WoosimPrnMng? = null

    fun printBluetooth(onPrintFinished: (() -> Unit)? = null) {
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
            printRecipe()
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

    private fun printRecipe(onPrintFinished: (() -> Unit)? = null){
        try {
            val bluetoothAddress = BluetoothPrintersConnections.selectFirstPaired().device.address
            val recipe = WarungPojokPrinter(requireContext(), order, onPrintFinished)
            //Connect to the printer and after successful connection issue the print command.
            mPrnMng = printerFactory.createPrnMng(requireContext(), bluetoothAddress, recipe)
        }catch (e:Exception){
            showToast(e.message)
        }
    }


    /*==============================================================================================
    ===================================ESC/POS PRINTER PART=========================================
    ==============================================================================================*/
    open fun  printIt(printerConnection: DeviceConnection?, onPrintFinished: (() -> Unit)? = null) {
        try {
            Log.d("PRINT", "preparing on printing.. $order")
            val format = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss", Locale.US)
            val printer = EscPosPrinter(printerConnection, 203, 48f, 32)
            printer
                .printFormattedText(
                    "[C]<font size='small'>Jl. Rambutan raya No. 1D RT 003/001, Kec. Pancoran Mas, Kota Depok</font>\n" +
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
                            "[C]${printClosingMessage(order.type)}"
                )


            Log.d("PRINT", "preparing print finish")
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

    private fun printMenus(menus: List<Menu>):String{
        var menuPrint = emptyString()
        menus.forEach { menu->
            menuPrint = menuPrint + "${menu.quantity} ${menu.name} [R]${toCurrencyFormat(menu.totalPrice)}\n" +
                    "[L] ${menu.additionalInformation}\n"
        }
        return menuPrint
    }

    private fun printClosingMessage(orderType: Int):String{
        return when(orderType){
            OrderTypeEnum.DINE_IN.type -> "po message"
            OrderTypeEnum.TAKE_AWAY.type -> "take away message"
            OrderTypeEnum.PRE_ORDER.type -> "po message"
                    else -> "po message"
        }
    }

    fun showBluetoothListDialog(){
        generateCustomAlertDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_bluetooth_list_dialog,
            isCancelable = false
        ).apply {



        }
    }

    private fun doDiscovery(){
        btnScan.gone()
        pgBar.visible()

        tvNewDevices.visible()


    }

}