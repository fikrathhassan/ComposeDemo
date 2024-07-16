package com.abzer.weatherapp.ui.screen.signup

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
import com.abzer.weatherapp.ui.screen.BackButton
import com.abzer.weatherapp.ui.screen.Background
import com.abzer.weatherapp.ui.screen.FullScreenProgressLoader
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.util.Extensions.showToast

@Composable
fun SignupScreen(
    navController: NavController
) {

    /**
     * Local variables
     */
    val emptyUser = User(
        id = null,
        name = "",
        email = "",
        password = "",
        phone = ""
    )
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val pattern = Regex("^\\d+\$")
    val viewModel = hiltViewModel<SignupVM>()
    val isLoading by viewModel.isLoading.collectAsState()
    val isUserRegistered by viewModel.isUserRegistered.collectAsState()
    val isUserAdded by viewModel.isUserAdded.collectAsState()
    var user by remember { mutableStateOf(emptyUser) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    /**
     * Functions
     */
    fun checkEmailAndPhone() {
        if (user.checkNameIsEmpty()) {
            context.showToast("Please enter a name!")
        } else if (user.checkEmailIsEmpty()) {
            context.showToast("Please enter an email address!")
        } else if (user.checkEmailIsNotValid()) {
            context.showToast("Please enter a valid email address!")
        } else if (user.checkPasswordIsEmpty()) {
            context.showToast("Please enter a password!")
        } else if (user.checkPhoneIsEmpty()) {
            context.showToast("Please enter a mobile number!")
        } else if (user.checkPhoneIsNotValid()) {
            context.showToast("Please enter a valid 10 digit mobile number!")
        } else {
            focusManager.clearFocus()
            viewModel.isUserExist(user.email ?: "")
        }
    }

    Background {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
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
                    enabled = !isLoading,
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
    }

    LaunchedEffect(isUserRegistered) {
        if (isUserRegistered == true) {
            context.showToast("User already registered!")
        }

        if (isUserRegistered == false) {
            viewModel.addUser(user)
        }

        viewModel.resetUserRegistered()
    }

    LaunchedEffect(isUserAdded) {
        if (isUserAdded == false) {
            context.showToast("Unable to add user!")
        }

        if (isUserAdded == true) {
            context.showToast("User registered successfully!")
            user = emptyUser
        }

        viewModel.resetUserAdded()
    }

}

@Preview(showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    AppTheme {
        SignupScreen(
            navController = rememberNavController()
        )
    }
}