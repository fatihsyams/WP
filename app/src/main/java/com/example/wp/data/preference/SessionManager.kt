package com.example.wp.data.preference

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val KEY_NAME = "NAMA"
    private val KEY_PASS = "PASS"
    private val KEY_BOOLEAN = "BOOLEAN"
    private val KEY_TOKEN = "TOKEN"
    private val KEY_ORDER = "ORDER"


    var mSharedPrefence: SharedPreferences? = null
    var mEditor: SharedPreferences.Editor? = null

    init {
        mSharedPrefence = context.getSharedPreferences(KEY_NAME, Context.MODE_PRIVATE)
    }

    fun saveEmail(email: String) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putString(KEY_NAME, email)?.apply()
    }

    fun sevePass(pass: String) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putString(KEY_PASS, pass)?.apply()
    }

    fun saveToken(token: String) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putString(KEY_TOKEN, token)?.apply()
    }

    fun getToken(): String? {
        return mSharedPrefence?.getString(KEY_TOKEN, null)
    }

    fun saveUserLogin(boolean: Boolean) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putBoolean(KEY_BOOLEAN, boolean)?.apply()
    }

    fun isUserLogin(): Boolean {
        return mSharedPrefence?.getBoolean(KEY_BOOLEAN, false)!!
    }

    fun saveOrderSaved(boolean: Boolean) {
        mEditor = mSharedPrefence?.edit()
        mEditor?.putBoolean(KEY_ORDER, boolean)?.apply()
    }

    fun isOrderSaved(): Boolean {
        return mSharedPrefence?.getBoolean(KEY_ORDER, false)!!
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