package com.abzer.weatherapp.util

import android.util.Log

object CommonFunctions {

    fun printLog(message: String?) {
        Log.d("printLog", message ?: "data is null")
    }

}