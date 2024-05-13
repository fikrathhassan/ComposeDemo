package com.abzer.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abzer.weatherapp.ui.screen.HomeScreen
import com.abzer.weatherapp.ui.screen.LoginScreen
import com.abzer.weatherapp.ui.screen.SignupScreen
import com.abzer.weatherapp.ui.screen.UsersScreen
import com.abzer.weatherapp.ui.screen.WelcomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {

    NavHost(
        modifier = modifier,
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