package com.example.wp.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

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