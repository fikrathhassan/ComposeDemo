package com.abzer.weatherapp.ui.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.data.model.Credentials
import com.abzer.weatherapp.ui.activity.HomeActivity
import com.abzer.weatherapp.ui.activity.MainActivity
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.LoginVM
import com.abzer.weatherapp.util.CommonFunctions
import com.abzer.weatherapp.util.Extensions.showToast
import com.abzer.weatherapp.util.Users
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginVM? = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    var credentials by remember { mutableStateOf(Credentials()) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isEnabled = remember { true }
    var isLoading by remember { mutableStateOf(false) }

    fun showLoader() {
        isLoading = true
    }

    fun hideLoader() {
        isLoading = false
    }

    fun enableButton() {
        isEnabled = true
    }

    fun disableButton() {
        isEnabled = false
    }

    fun checkEmail() {
        if (!credentials.checkEmailIsNotEmpty()) {
            context.showToast("Please enter an email address!")
        } else if (!credentials.checkEmailIsValid()) {
            context.showToast("Please enter a valid email address!")
        } else if (!credentials.checkPasswordIsNotEmpty()) {
            context.showToast("Please enter a password!")
        } else {
            disableButton()
            showLoader()
            viewModel?.findUserByEmail(credentials.email)
        }
    }

    CommonFunctions.printLog("LoginScreen")

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.screen_padding)
        ) {
            BackButton(navController)
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.log_in_to_account),
                color = AppTheme.colors.textPrimary,
                fontSize = AppTheme.fontDimens.headingPrimary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = credentials.email,
                onValueChange = { credentials = credentials.copy(email = it) },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .align(Alignment.CenterHorizontally),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email),
                        tint = AppTheme.colors.primary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                placeholder = { Text(stringResource(R.string.enter_your_email)) },
                label = { Text(stringResource(R.string.email)) },
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = AppTheme.colors.textPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = credentials.password,
                onValueChange = { credentials = credentials.copy(password = it) },
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = stringResource(R.string.password),
                        tint = AppTheme.colors.primary
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
                        val image = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        Icon(
                            imageVector = image,
                            contentDescription = if (isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                                R.string.show_password
                            ),
                            tint = AppTheme.colors.primary
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                placeholder = { Text(stringResource(R.string.enter_your_password)) },
                label = { Text(stringResource(R.string.password)) },
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = AppTheme.colors.textPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = AppTheme.dimens.inner_padding)
                    .clickable(
                        onClick = {
                            credentials = credentials.copy(staySignedIn = !credentials.staySignedIn)
                        }
                    )
                    .align(Alignment.CenterHorizontally),
            ) {
                Checkbox(
                    checked = credentials.staySignedIn,
                    onCheckedChange = null,
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = AppTheme.colors.textPrimary,
                        checkedColor = AppTheme.colors.textPrimary,
                        checkmarkColor = AppTheme.colors.primary,
                    )
                )
                Spacer(Modifier.size(AppTheme.dimens.inner_padding))
                Text(
                    text = stringResource(R.string.stay_signed_in),
                    color = AppTheme.colors.textPrimary
                )
            }
            Button(
                onClick = { checkEmail() },
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = AppTheme.dimens.inner_padding)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colors.primary,
                    disabledContainerColor = AppTheme.colors.primary.copy(alpha = 0.5f ),
                ),
                contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding),
                shape = RoundedCornerShape(AppTheme.dimens.button_primary_radius)
            ) {
                Text(
                    modifier = Modifier
                        .alpha(alpha = if (isEnabled) 1f else 0.25f),
                    text = stringResource(id = R.string.login),
                    textAlign = TextAlign.Center,
                    fontSize = AppTheme.fontDimens.bodyPrimary,
                    color = AppTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        if (isLoading) {
            FullScreenProgressLoader()
        }
    }

    LaunchedEffect(Unit) {
        viewModel?.isUserRegistered?.observe(lifecycleOwner) {
            if (!it) {
                enableButton()
                hideLoader()
                context.showToast("User not registered!")
            } else {
                viewModel.findUser(credentials.email, credentials.password)
            }
        }
        viewModel?.user?.observe(lifecycleOwner) {
            if (it == null) {
                enableButton()
                context.showToast("Incorrect credentials!")
            } else {
                context as MainActivity

                context.dataStoreManager.apply {
                    scope.launch {
                        launch {
                            setLoggedIn(credentials.staySignedIn)
                        }
                        if (it.email.equals("admin@admin.com")) {
                            setLoggedUser(Users.ADMIN)
                        } else {
                            setLoggedUser(Users.USER)
                        }
                    }
                }

                context.apply {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
            }
            hideLoader()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(
            navController = rememberNavController(),
            viewModel =  null
        )
    }
}