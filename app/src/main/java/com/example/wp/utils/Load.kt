package com.example.wp.utils

sealed class Load<out T> {
    object Loading : Load<Nothing>()
    data class Fail<T>(val error: Throwable) : Load<T>()
    data class Success<T : Any>(val data: T) : Load<T>()
}