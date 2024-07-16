package com.abzer.weatherapp.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.screen.BackPressCompose
import com.abzer.weatherapp.ui.screen.BackgroundWithBottomNavigation
import com.abzer.weatherapp.ui.screen.PrimaryButton
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.util.Extensions.showToast
import com.abzer.weatherapp.util.Users

@Composable
fun HomeScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeVM>()
    val isLoading by viewModel.isLoading.collectAsState()
    val loggedUser by viewModel.loggedUser.collectAsState(initial = Users.USER.name)
    val isLoggedOut by viewModel.isLoggedOut.collectAsState()

    BackPressCompose(context = context)

    BackgroundWithBottomNavigation(
        navController = navController,
        isAdminUser = loggedUser == Users.ADMIN.name
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.screen_padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Home",
                fontSize = AppTheme.fontDimens.headingPrimary,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.textPrimary,
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(1f))
            PrimaryButton(
                buttonText = "Logout",
                onClick = { viewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth(.9f),
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    LaunchedEffect(isLoggedOut) {
        if (isLoggedOut) {
            context.showToast("User has been logged out!")
            navController.navigate(
                route = AppScreens.WELCOME.route,
            ) {
                popUpTo(AppScreens.HOME.route) {
                    inclusive = true
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            navController = rememberNavController()
        )
    }
}