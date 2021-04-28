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
import java.text.SimpleDateFormat
import java.util.*

const val PERMISSION_BLUETOOTH = 1

abstract class WarungPojokPrinterFragment : WarungPojokFragment() {

    abstract val order: OrderResult

    private var mBtAdapter: BluetoothAdapter? = null
    private var mPairedDevicesArrayAdapter: ArrayAdapter<String>? = null
    private var mNewDevicesArrayAdapter: ArrayAdapter<String>? = null

    private var mPrnMng: WoosimPrnMng? = null

    fun printBluetooth(
        onPrintFinished: (() -> Unit)? = null,
        onErrorOccured: ((message: String) -> Unit)? = null,
        isBill: Boolean = false
    ) {
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
            printRecipe(onPrintFinished, onErrorOccured,isBill)
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

    private fun printRecipe(
        onPrintFinished: (() -> Unit)? = null,
        onErrorOccured: ((message: String) -> Unit)? = null,
        isBill: Boolean
    ) {
        try {
            Log.d("PRINT", "preparing on printing.. $order")

            // Get the local Bluetooth adapter
            mBtAdapter = BluetoothAdapter.getDefaultAdapter()

            if (mBtAdapter != null) {
                // Get a set of currently paired devices
                val pairedDevices = mBtAdapter?.bondedDevices
                val activeBluetooth = pairedDevices?.firstOrNull()

                activeBluetooth?.let {
                    Log.d("PRINT", "get bluetooth $activeBluetooth")
                    val bluetoothAddress = activeBluetooth.address
                    val recipe =
                        WarungPojokPrinter(requireContext(), order, isBill, onPrintFinished)
                    //Connect to the printer and after successful connection issue the print command.
                    mPrnMng =
                        printerFactory.createPrnMng(requireContext(), bluetoothAddress, recipe)
                }
            } else {
                onErrorOccured?.invoke("Printer not found")
            }

        } catch (e: Exception) {
            onErrorOccured?.invoke("Printer error ${e.message}")
        }
    }


    /*==============================================================================================
    ===================================ESC/POS PRINTER PART=========================================
    ==============================================================================================*/
    open fun printIt(printerConnection: DeviceConnection?, onPrintFinished: (() -> Unit)? = null) {
        try {
            Log.d("PRINT", "preparing on printing.. $order")
            val format = SimpleDateFormat("dd-MM-yyyy 'at' HH:mm:ss", Locale.US)
            val printer = EscPosPrinter(printerConnection, 203, 48f, 32)
            printer
                .printFormattedText(
                    "[C]<font size='large'>Bale Jentera Food Corner\n" +
                    "[C]<font size='small'>Jl. Rambutan raya No. 1D RT 003/001, Kec. Pancoran Mas, Kota Depok</font>\n" +
                            "[C]<font size='small'>+62 819-0609-4972</font>\n" +
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

    private fun printMenus(menus: List<Menu>): String {
        var menuPrint = emptyString()
        menus.forEach { menu ->
            menuPrint =
                menuPrint + "${menu.quantity} ${menu.name} [R]${toCurrencyFormat(menu.totalPrice)}\n" +
                        "[L] ${menu.additionalInformation}\n"
        }
        return menuPrint
    }

    private fun printClosingMessage(orderType: Int): String {
        return when (orderType) {
            OrderTypeEnum.DINE_IN.type -> "Terima Kasih Atas Kunjungannya"
            OrderTypeEnum.TAKE_AWAY.type -> "Terima Kasih Atas Orderannya"
            OrderTypeEnum.PRE_ORDER.type -> "Selamat Menikmati"
            else -> "Selamat Menikmati"
        }
    }

    fun showBluetoothListDialog() {
        generateCustomAlertDialog(
            context = requireContext(),
            layoutRes = R.layout.layout_bluetooth_list_dialog,
            isCancelable = false
        ).apply {


            // Initialize array adapters. One for already paired devices and
            // one for newly discovered devices
            mPairedDevicesArrayAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_bluetooth_name
            )
            mNewDevicesArrayAdapter = ArrayAdapter(requireContext(), R.layout.item_bluetooth_name)

            // Get the local Bluetooth adapter
            mBtAdapter = BluetoothAdapter.getDefaultAdapter()

            // Get a set of currently paired devices
            val pairedDevices = mBtAdapter?.getBondedDevices()

            // If there are paired devices, add each one to the ArrayAdapter

            // If there are paired devices, add each one to the ArrayAdapter
            if (!pairedDevices.isNullOrEmpty()) {
                tvPairedDevices.visible()
                for (device in pairedDevices) {
                    mPairedDevicesArrayAdapter!!.add(
                        """
                ${device.name}
                ${device.address}
                """.trimIndent()
                    )
                }
            } else {
                val noDevices = resources.getText(R.string.none_paired).toString()
                mPairedDevicesArrayAdapter!!.add(noDevices)
            }

        }
    }

    private fun doDiscovery() {
        if (mBtAdapter?.isDiscovering == true) {
            mBtAdapter?.cancelDiscovery()
        }
        mBtAdapter?.startDiscovery()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Make sure we're not doing discovery anymore

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter!!.cancelDiscovery()
        }

    }

}