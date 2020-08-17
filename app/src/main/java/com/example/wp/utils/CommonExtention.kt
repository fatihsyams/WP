package com.example.wp.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wp.R
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File


fun String.toRequestBody(): RequestBody? {
    return RequestBody.create(MediaType.parse("text/plain"), this)
}

fun File.toRequestBody():RequestBody{
    return RequestBody.create(MediaType.parse("image/png"), this)
}

fun AppCompatActivity.loadFragment(layoutResourceId:Int, fragment: Fragment, isBackStack:Boolean = false) {
    supportFragmentManager.beginTransaction()
        .replace(layoutResourceId, fragment)
        .addToBackStack(if (isBackStack) fragment.tag else null)
        .commit()
}

fun Fragment.loadFragment(layoutResourceId:Int, fragment: Fragment, isBackStack:Boolean = false) {
    childFragmentManager.beginTransaction()
        .replace(layoutResourceId, fragment)
        .addToBackStack(if (isBackStack) fragment.tag else null)
        .commit()
}

fun Fragment.showToast(message:String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}