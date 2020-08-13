package com.example.wp.data.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager {
    private val KEY_NAME  = "NAMA"
    private val KEY_PASS = "PASS"
    private val KEY_BOOLEAN = "BOOLEAN"


    var mSharedPrefence: SharedPreferences? = null
    var mEditor: SharedPreferences.Editor? = null

    fun SessionManager(context: Context) {
        mSharedPrefence = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
    }

    fun saveEmail(email : String) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putString(KEY_NAME, email)?.apply()
    }

    fun sevePass(pass: String) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putString(KEY_PASS, pass)?.apply()
    }

    fun saveBoolean(boolean: Boolean) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putBoolean(KEY_BOOLEAN, boolean)?.apply()
    }
    fun getBoolean(): Boolean {
        return mSharedPrefence?.getBoolean(KEY_BOOLEAN, false)!!
    }

    fun getEmail(): String? {
        return mSharedPrefence?.getString(KEY_NAME, null)
    }

    fun getPass(): String? {
        return mSharedPrefence?.getString(KEY_PASS, null)
    }

    fun logout() {
        mEditor = mSharedPrefence?.edit()
        mEditor?.clear()?.commit()
    }



}