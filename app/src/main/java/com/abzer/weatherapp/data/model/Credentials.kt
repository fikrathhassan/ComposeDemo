package com.abzer.weatherapp.data.model

import android.util.Patterns

data class Credentials(
    var email: String = "",
    var password: String = "",
    var staySignedIn: Boolean = false
) {

    fun checkEmailIsEmpty(): Boolean {
        return email.isBlank()
    }

    fun checkEmailIsNotValid(): Boolean {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkPasswordIsEmpty(): Boolean {
        return password.isBlank()
    }

}
