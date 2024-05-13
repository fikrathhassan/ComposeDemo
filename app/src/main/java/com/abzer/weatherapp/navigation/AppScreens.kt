package com.abzer.weatherapp.navigation

sealed class AppScreens(val name: String, val route: String) {

    data object WELCOME : AppScreens("Welcome", "welcome")
    data object LOGIN : AppScreens("Login", "login")
    data object SIGNUP : AppScreens("Signup", "login_signup")
    data object HOME : AppScreens("Home", "home")
    data object USERS : AppScreens("Users", "home_users")

    fun routeWithArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}