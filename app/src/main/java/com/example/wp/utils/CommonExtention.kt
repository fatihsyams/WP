package com.example.wp.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wp.R
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.io.File


fun String.toRequestBody(): RequestBody? {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}

fun File.toRequestBody():RequestBody{
    return RequestBody.create("image/png".toMediaTypeOrNull(), this)
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

fun Fragment.removeFragment(){
    activity?.supportFragmentManager?.popBackStack()
}

fun AppCompatActivity.resfreshFragment(fragment: Fragment){
    supportFragmentManager.beginTransaction().detach(fragment).attach(fragment).commitAllowingStateLoss()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}