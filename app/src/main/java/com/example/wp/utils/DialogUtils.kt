package com.example.wp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.wp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


fun generateCustomAlertDialog(
    context:Context,
    layoutRes:Int,
    isCancelable:Boolean
):AlertDialog{
    return AlertDialog.Builder(context).create().apply {
        val view = LayoutInflater.from(context).inflate(layoutRes, null)
        setView(view)
        setCancelable(isCancelable)
        show()
    }

}

fun generateCustomBottomSheetDialog(
    context:Context,
    layoutRes:Int,
    isCancelable:Boolean,
    isExpandMode:Boolean = false
):BottomSheetDialog{
    return BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme).apply {
        val view = LayoutInflater.from(context).inflate(layoutRes, null)
        setContentView(view)
        setCancelable(isCancelable)

        setOnShowListener {dialog->
           if (isExpandMode){
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