package com.abzer.weatherapp.util

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

object Extensions {

    fun Context.showToast(message: String?) {
        if (!message.isNullOrBlank()) {
            Toast.makeText(this, message,Toast.LENGTH_LONG).show()
        }
    }

    fun NavController.canGoBack(): Boolean {
        return currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
    }

}