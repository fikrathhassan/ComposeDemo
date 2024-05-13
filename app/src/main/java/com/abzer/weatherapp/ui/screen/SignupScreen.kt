package com.abzer.weatherapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.SignupVM
import com.abzer.weatherapp.util.Extensions.showToast

@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: SignupVM? = hiltViewModel()
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    var user by remember { mutableStateOf(User()) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isEnabled = remember { true }
    val pattern = Regex("^\\d+\$")
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

    fun checkEmailAndPhone() {
        if (!user.checkNameIsNotEmpty()) {
            context.showToast("Please enter a name!")
        } else if (!user.checkEmailIsNotEmpty()) {
            context.showToast("Please enter an email address!")
        } else if (!user.checkEmailIsValid()) {
            context.showToast("Please enter a valid email address!")
        } else if (!user.checkPasswordIsNotEmpty()) {
            context.showToast("Please enter a password!")
        } else if (!user.checkPhoneIsNotEmpty()) {
            context.showToast("Please enter a mobile number!")
        } else if (!user.checkPhoneIsValid()) {
            context.showToast("Please enter a valid 10 digit mobile number!")
        } else {
            disableButton()
            focusManager.clearFocus()
            showLoader()
            viewModel?.findUserByEmail(user.email ?: "")
        }
    }

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
                text = stringResource(R.string.create_new_account),
                fontSize = AppTheme.fontDimens.headingPrimary,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.textPrimary,
            )
            Spacer(modifier = Modifier.weight(1f))
            TextField(
                value = user.name ?: "",
                onValueChange = { user = user.copy(name = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.inner_padding),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(R.string.email),
                        tint = AppTheme.colors.primary
                    )
                },
                label = {
                    Text(text = stringResource(R.string.full_name))
                },
                placeholder = {
                    Text(text = stringResource(R.string.enter_your_name))
                },
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = AppTheme.colors.textPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            TextField(
                value = user.email ?: "",
                onValueChange = { user = user.copy(email = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.inner_padding),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = stringResource(R.string.email),
                        tint = AppTheme.colors.primary
                    )
                },
                label = { Text(text = stringResource(R.string.email_address)) },
                placeholder = { Text(text = stringResource(R.string.sample_email)) },
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = AppTheme.colors.textPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            TextField(
                value = user.password ?: "",
                onValueChange = { user = user.copy(password = it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.inner_padding),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = stringResource(R.string.password),
                        tint = AppTheme.colors.primary
                    )
                },
                trailingIcon = {
                    val image = if (isPasswordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    IconButton(
                        onClick = { isPasswordVisible = !isPasswordVisible }
                    ) {
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
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                label = { Text(text = stringResource(R.string.create_password)) },
                placeholder = { Text(text = stringResource(R.string.enter_your_password)) },
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
                ),
            )
            TextField(
                value = user.phone ?: "",
                onValueChange = {
                    if (it.matches(pattern) && it.length <= 10) {
                        user = user.copy(phone = it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.inner_padding),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = stringResource(R.string.phone),
                        tint = AppTheme.colors.primary
                    )
                },
                label = { Text(text = stringResource(R.string.phone)) },
                placeholder = { Text(text = stringResource(R.string.phone_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = AppTheme.colors.textPrimary,
                    unfocusedContainerColor = AppTheme.colors.textPrimary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                )
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { checkEmailAndPhone() },
                enabled = isEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.inner_padding),
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
                    text = stringResource(id = R.string.sign_up),
                    textAlign = TextAlign.Center,
                    fontSize = AppTheme.fontDimens.bodyPrimary,
                    color = AppTheme.colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (isLoading) {
            FullScreenProgressLoader()
        }
    }

    LaunchedEffect(Unit) {
        viewModel?.isUserRegistered?.observe(lifecycleOwner) {
            if (it) {
                enableButton()
                hideLoader()
                context.showToast("User already registered!")
            } else {
                viewModel.addUser(user)
            }
        }
        viewModel?.isUserAdded?.observe(lifecycleOwner) {
            enableButton()
            hideLoader()
            if (it) {
                user = User()
                context.showToast("User registered successfully!")
            } else {
                context.showToast("Unable to add user!")
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    AppTheme {
        SignupScreen(
            navController = rememberNavController(),
            viewModel =  null
        )
    }
}