package com.abzer.weatherapp.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.ui.activity.HomeActivity
import com.abzer.weatherapp.ui.activity.MainActivity
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.HomeVM
import com.abzer.weatherapp.util.CommonFunctions
import com.abzer.weatherapp.util.Extensions.showToast

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeVM? = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val isLogoutButtonEnabled = remember { mutableStateOf(true) }

    CommonFunctions.printLog("HomeScreen")

    BackPressCompose(context = context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.dimens.screen_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text("Home Screen")
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            buttonText = "Logout",
            onClick = {
                isLogoutButtonEnabled.value = false
                viewModel?.logout()
            },
            modifier = Modifier
                .fillMaxWidth(.9f),
            enabled = isLogoutButtonEnabled.value
        )
    }

    LaunchedEffect(Unit) {
        viewModel?.isLoggedOut?.observe(lifecycleOwner) {
            if (it) {
                isLogoutButtonEnabled.value = false
                (context as HomeActivity).apply {
                    context.showToast("User logged out successfully!")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } else {
                isLogoutButtonEnabled.value = true
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            navController = rememberNavController(),
            viewModel =  null
        )
    }
}