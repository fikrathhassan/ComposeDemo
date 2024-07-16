package com.abzer.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abzer.weatherapp.ui.screen.home.HomeScreen
import com.abzer.weatherapp.ui.screen.login.LoginScreen
import com.abzer.weatherapp.ui.screen.signup.SignupScreen
import com.abzer.weatherapp.ui.screen.users.UsersScreen
import com.abzer.weatherapp.ui.screen.welcome.WelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppScreens.WELCOME.route) {
            WelcomeScreen(navController = navController)
        }
        composable(AppScreens.LOGIN.route) {
            LoginScreen(navController = navController)
        }
        composable(AppScreens.SIGNUP.route) {
            SignupScreen(navController = navController)
        }
        composable(AppScreens.HOME.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreens.USERS.route) {
            UsersScreen(navController = navController)
        }
    }
}