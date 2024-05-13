package com.abzer.weatherapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.abzer.weatherapp.R
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.theme.AppTheme

@Composable
fun WelcomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.dimens.screen_padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.welcome),
            fontSize = AppTheme.fontDimens.titlePrimary,
            color = AppTheme.colors.textPrimary
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
        Text(
            text = stringResource(R.string.intro_message),
            fontSize = AppTheme.fontDimens.titleSecondary,
            color = AppTheme.colors.textPrimary.copy(alpha =.5f)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = "https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/76522/hot-air-balloon-clipart-xl.png",
                contentDescription = stringResource(id = R.string.welcome_screen_image)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { navController.navigate(route = AppScreens.LOGIN.route) },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.primary
            ),
            contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                textAlign = TextAlign.Center,
                fontSize = AppTheme.fontDimens.bodyPrimary,
                color = AppTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
        OutlinedButton(
            onClick = { navController.navigate(AppScreens.SIGNUP.route) },
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding),
            border = BorderStroke(
                width = 1.dp,
                color = AppTheme.colors.textPrimary
            )
        ) {
            Text(
                text = stringResource(id = R.string.no_account_yet_msg),
                textAlign = TextAlign.Center,
                fontSize = AppTheme.fontDimens.bodyPrimary,
                color = AppTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}