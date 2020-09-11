package com.example.wp.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.wp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*


fun generateCustomAlertDialog(
    context: Context,
    layoutRes: Int,
    isCancelable: Boolean
): AlertDialog {
    return AlertDialog.Builder(context).create().apply {
        val view = LayoutInflater.from(context).inflate(layoutRes, null)
        setView(view)
        setCancelable(isCancelable)
        show()
    }

}

fun generateCustomBottomSheetDialog(
    context: Context,
    layoutRes: Int,
    isCancelable: Boolean,
    isExpandMode: Boolean = false
): BottomSheetDialog {
    return BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme).apply {
        val view = LayoutInflater.from(context).inflate(layoutRes, null)
        setContentView(view)
        setCancelable(isCancelable)

        setOnShowListener { dialog ->
            if (isExpandMode) {
                val d = dialog as BottomSheetDialog
                val bottomSheet =
                    d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
                BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!)
                    .setState(BottomSheetBehavior.STATE_EXPANDED)
            }
        }

        show()
    }

}

fun getOnDateSetListener(calendar: Calendar): OnDateSetListener? {
    return OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, monthOfYear)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.US)
    }
}

fun getDatePickerDialog(context: Context, calendar: Calendar): DatePickerDialog? {
    return DatePickerDialog(
        context, getOnDateSetListener(calendar), calendar
            .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
