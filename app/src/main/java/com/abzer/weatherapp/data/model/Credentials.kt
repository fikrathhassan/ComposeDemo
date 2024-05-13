package com.abzer.weatherapp.data.model

import android.util.Patterns

data class Credentials(
    var email: String = "",
    var password: String = "",
    var staySignedIn: Boolean = false
) {

    fun checkEmailIsNotEmpty(): Boolean {
        return email.isNotBlank()
    }

    fun checkEmailIsValid(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPasswordIsNotEmpty(): Boolean {
        return password.isNotBlank()
    }

}
