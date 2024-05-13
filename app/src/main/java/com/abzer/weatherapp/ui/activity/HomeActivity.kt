package com.abzer.weatherapp.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.navigation.AppNavHost
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.screen.BottomNavigationBar
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.HomeAVM
import com.abzer.weatherapp.util.Users
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val viewModel: HomeAVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val user = dataStoreManager.loggedUser.collectAsState(initial = Users.USER.name)

            fun isAdminUser(): Boolean = user.value == Users.ADMIN.name

            AppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Color.Transparent,
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            isAdminUser = isAdminUser()
                        )
                    }
                ) {
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
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        AppNavHost(
                            navController = navController,
                            startDestination = AppScreens.HOME.route,
                            modifier = Modifier
                                    .fillMaxSize(),
                        )
                    }
                }
            }
        }
    }

}