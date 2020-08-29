package com.example.wp.utils

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {

    fun parseError(response: Response<*>): ApiError {

        val gson = GsonBuilder().create()
        val error: ApiError

        try {
            error = gson.fromJson(response.errorBody()?.string(), ApiError::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d("error", it) }
            return ApiError()
        }
        return error
    }
}

data class ApiError(val code: String, val message: String, val status: String, val error: String) {
    constructor() : this("", "", "", "")
}