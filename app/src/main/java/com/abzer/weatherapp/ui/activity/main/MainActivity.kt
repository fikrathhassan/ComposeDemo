package com.abzer.weatherapp.ui.activity.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.navigation.AppNavHost
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainAVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setOnExitAnimationListener { viewProvider ->
                ObjectAnimator.ofFloat(
                    viewProvider.view,
                    "scaleX",
                    0.5f, 0f
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = 1000
                    doOnEnd { viewProvider.remove() }
                    start()
                }
                ObjectAnimator.ofFloat(
                    viewProvider.view,
                    "scaleY",
                    0.5f, 0f
                ).apply {
                    interpolator = OvershootInterpolator()
                    duration = 1000
                    doOnEnd { viewProvider.remove() }
                    start()
                }
            }
            setKeepOnScreenCondition{
                viewModel.isSplashScreenVisible.value
            }
        }

        setContent {

            val isStayLoggedIn by viewModel.isStayLoggedIn.collectAsState(initial = false)

            val startDestination = if (!isStayLoggedIn) {
                AppScreens.WELCOME.route
            } else {
                AppScreens.HOME.route
            }

            AppTheme {
                AppNavHost(
                    navController = rememberNavController(),
                    startDestination = startDestination
                )

            }
        }

    }

}