package com.abzer.weatherapp.ui.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.navigation.AppNavHost
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.MainAVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

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
            AppTheme {

                val isLoggedIn = dataStoreManager.isLoggedIn.collectAsState(initial = false)
                if (isLoggedIn.value) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                    return@AppTheme
                }

                Scaffold {
                    Surface {
                        Box(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(R.drawable.background),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )
                            AppNavHost(
                                navController = rememberNavController(),
                                startDestination = AppScreens.WELCOME.route,
                                modifier = Modifier
                                    .fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }

//        setContent {
//
//            val navController = rememberNavController()
//            val isLoggedIn = dataStoreManager.isLoggedIn.collectAsState(initial = false)
//            val isAdminUser = rememberSaveable { mutableStateOf(false) }
//            val isBottomNavigationBarShown = rememberSaveable { mutableStateOf(false) }
//
//            AppTheme {
//                Scaffold(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    containerColor = Color.Transparent,
//                    bottomBar = {
//                        AnimatedVisibility(
//                            visible = isBottomNavigationBarShown.value,
//                            enter = slideInVertically(initialOffsetY = { it }),
//                            exit = slideOutVertically(targetOffsetY = { it }),
//                            content = {
//                                BottomNavigationBar(
//                                    navController = navController,
//                                    isAdminUser = isAdminUser
//                                )
//                            }
//                        )
//                    }
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .padding(it)
//                            .fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            modifier = Modifier
//                                .fillMaxSize(),
//                            painter = painterResource(R.drawable.background),
//                            contentDescription = "",
//                            contentScale = ContentScale.FillBounds
//                        )
//                        AppNavHost(
//                            isLoggedIn = isLoggedIn.value,
//                            navController = navController,
//                            modifier = Modifier
//                                    .fillMaxSize(),
//                        ) { showBottomNavigation, adminUser ->
//                            isBottomNavigationBarShown.value = showBottomNavigation
//                            isAdminUser.value = adminUser
//                        }
//                    }
//                }
//            }
//        }
    }

}