package com.example.wp.utils

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun emptyString() = ""

fun getCurrentDate(format: String): String {
    val date = Calendar.getInstance().time
    val newFormat = SimpleDateFormat(format)
    return newFormat.format(date)
}

fun changeFormatStringDate(
    date: String,
    formatNew: String,
    formatOld: String = "yyyy-MM-dd"
): String {
    val oldFormat = SimpleDateFormat(formatOld, Locale("in"))
    val oldDate = oldFormat.parse(date)
    val newFormat = SimpleDateFormat(formatNew, Locale("in"))
    return newFormat.format(oldDate)
}

fun changeFormatLongDate(date: Long, format: String): String {
    val date = Date(date)
    val df2 = SimpleDateFormat(format, Locale("in"))
    return df2.format(date)
}

fun clearForm(views:List<View>){
    views.forEach { view->
        when(view){
            is AppCompatEditText -> {
                view.setText(emptyString())
            }
        }
    }
}

fun toCurrencyFormat(number:Double):String{
    val formatter: NumberFormat = DecimalFormat("#,###")
    return formatter.format(number)
}

fun isFormComplete(views: List<TextInputLayout>): Boolean {
    return views.none { it.editText!!.text.isEmpty() }
}
