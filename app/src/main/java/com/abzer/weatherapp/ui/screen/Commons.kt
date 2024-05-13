package com.abzer.weatherapp.ui.screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.abzer.weatherapp.R
import com.abzer.weatherapp.navigation.AppScreens
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.util.Extensions.canGoBack
import com.abzer.weatherapp.util.Extensions.showToast
import kotlinx.coroutines.delay

/**
 * Double tap to exit
 */

sealed class BackPress {
    data object Idle : BackPress()
    data object InitialTouch : BackPress()
}

@Composable
fun BackPressCompose(
    context: Context
) {

    var showToast by remember { mutableStateOf(false) }
    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }

    if (showToast) {
        context.showToast("Press again to exit")
        showToast = false
    }

    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2_000)
            backPressState = BackPress.Idle
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        backPressState = BackPress.InitialTouch
        showToast = true
    }
}

/**
 * Progress loader
 */

@Composable
fun FullScreenProgressLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { }
            .background(
                color = Color.Black.copy(alpha = 0.5f)
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = AppTheme.dimens.progressbar_size),
            color = AppTheme.colors.primary,
            trackColor = AppTheme.colors.textPrimary,
            strokeWidth = AppTheme.dimens.progressbar_stroke,
        )
    }
}

@Composable
fun ProgressLoader() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(size = AppTheme.dimens.progressbar_size),
        color = AppTheme.colors.primary,
        trackColor = AppTheme.colors.textPrimary,
        strokeWidth = AppTheme.dimens.progressbar_stroke,
    )
}

@Preview(showSystemUi = true)
@Composable
fun ProgressLoaderPreview() {
    AppTheme {
        FullScreenProgressLoader()
    }
}

/**
 * Primary button
 */

@Composable
fun PrimaryButton(
    buttonText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonColors(
            containerColor = AppTheme.colors.primary,
            disabledContainerColor = AppTheme.colors.primary.copy(alpha = 0.5f),
            contentColor = AppTheme.colors.textPrimary,
            disabledContentColor = AppTheme.colors.textPrimary.copy(alpha = 0.5f)
        ),
        contentPadding = PaddingValues(0.dp),
        enabled = enabled
    ) {
        Text(
            text = buttonText,
            fontSize = AppTheme.fontDimens.bodyPrimary
        )
    }
}

/**
 * Back button
 */

@Composable
fun BackButton(navController: NavController) {
    IconButton(
        onClick = {
            if (navController.canGoBack()) {
                navController.popBackStack()
            }
        },
        modifier = Modifier
            .padding(bottom = AppTheme.dimens.inner_padding)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button),
            tint = AppTheme.colors.textPrimary
        )
    }
}

/**
 * Bottom navigation bar
 */

data class NavigationItem(
    val route: String? = null,
    val icon: ImageVector? = null,
    val title: Int? = null
) {
    fun navigationItems(): List<NavigationItem> {
        return listOf(
            NavigationItem(AppScreens.HOME.route, Icons.Filled.Home, R.string.home),
            NavigationItem(AppScreens.USERS.route, Icons.Filled.ManageAccounts, R.string.users)
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    isAdminUser: Boolean
) {

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
    ) {

        NavigationItem().navigationItems().forEach { item ->

            if (item.route?.equals(AppScreens.USERS.route) == true) {
                if (!isAdminUser) {
                    return@forEach
                }
            }

            val isSelected = navController.currentBackStackEntryAsState().value?.destination?.hierarchy?.any {
                it.route == item.route
            }

            NavigationBarItem(
                icon = {
                    Image(
                        imageVector = item.icon!!,
                        contentDescription = stringResource(id = item.title!!),
                        modifier = Modifier,
                        colorFilter = if (isSelected == true) {
                            ColorFilter.tint(AppTheme.colors.primary)
                        } else {
                            ColorFilter.tint(AppTheme.colors.primary.copy(alpha = 0.5f))
                        }
                    )
                },
                onClick = {
                    navController.navigate(item.route!!) {
                        popUpTo(AppScreens.HOME.route)
                        launchSingleTop = true
                    }
                },
                selected = isSelected == true,
                label = {
                    Text(
                        text = stringResource(id = item.title!!),
                        fontSize = AppTheme.fontDimens.bodyPrimary
                    )
                },
                colors = NavigationBarItemColors(
                    selectedTextColor = AppTheme.colors.primary,
                    selectedIconColor = AppTheme.colors.primary,
                    unselectedTextColor = AppTheme.colors.primary.copy(alpha = 0.5f),
                    unselectedIconColor = AppTheme.colors.primary.copy(alpha = 0.5f),
                    selectedIndicatorColor = AppTheme.colors.primary.copy(alpha = 0.25f),
                    disabledIconColor = AppTheme.colors.primary.copy(alpha = 0.1f),
                    disabledTextColor = AppTheme.colors.primary.copy(alpha = 0.1f)
                ),
            )
        }
    }

}