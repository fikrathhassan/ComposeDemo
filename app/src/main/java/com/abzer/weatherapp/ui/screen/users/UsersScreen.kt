package com.abzer.weatherapp.ui.screen.users

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.ui.screen.BackgroundWithBottomNavigation
import com.abzer.weatherapp.ui.screen.FullScreenProgressLoader
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.util.Extensions.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navController: NavController
) {

    /***
     * Local variables
     */
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val viewModel = hiltViewModel<UsersVM>()
    val isFullScreenLoaderVisible by viewModel.isLoading.collectAsState()
    val isUserUpdated by viewModel.isUserUpdated.collectAsState()
    val isUserDeleted by viewModel.isUserDeleted.collectAsState()
    val userList by viewModel.users.collectAsState(initial = emptyList())
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isAlertDialogVisible by remember { mutableStateOf(false) }

    fun resetSelectedUser() { selectedUser = null }

    fun showBottomSheet() { isBottomSheetVisible = true }
    fun hideBottomSheet() { isBottomSheetVisible = false }

    fun showAlertDialog() { isAlertDialogVisible = true }
    fun hideAlertDialog() { isAlertDialogVisible = false }

    @Composable
    fun ItemUser(user: User) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(AppTheme.dimens.cardRadius),
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.cardBackground
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.cardPadding)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_item_user),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(AppTheme.dimens.itemUserImage)
                        .clip(RoundedCornerShape(50)),
                )
                Column(
                    modifier = Modifier
                        .padding(start = AppTheme.dimens.inner_padding)
                        .weight(1F),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = user.name ?: "",
                        fontSize = AppTheme.fontDimens.bodyPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row {
                        ElevatedSuggestionChip(
                            onClick = {
                                selectedUser = user
                                showBottomSheet()
                                      },
                            label = {
                                Text(
                                    text = "View",
                                    fontSize = AppTheme.fontDimens.bodySecondary
                                )
                            },
                            colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                                containerColor = Color.Green
                            ),
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(15.dp)
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(AppTheme.dimens.inner_padding))
                        ElevatedSuggestionChip(
                            onClick = {
                                selectedUser = user
                                showAlertDialog()
                                      },
                            label = {
                                Text(
                                    text = "Delete",
                                    fontSize = AppTheme.fontDimens.bodySecondary,
                                    color = Color.White
                                )
                            },
                            colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                                containerColor = Color.Red
                            ),
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(15.dp),
                                    tint = Color.White
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BottomSheetWindow(user: User) {
        val pattern = Regex("^\\d+\$")
        val bottomSheetState = rememberModalBottomSheetState()
        var currentUser by remember { mutableStateOf(user) }
        var isEditable by remember { mutableStateOf(false) }

        fun enableEditView() { isEditable = true }
        fun disableEditView() { isEditable = false }

        fun checkEmailAndPhone() {
            if (currentUser.checkNameIsEmpty()) {
                context.showToast("Please enter a name!")
            } else if (currentUser.checkEmailIsEmpty()) {
                context.showToast("Please enter an email address!")
            } else if (currentUser.checkEmailIsNotValid()) {
                context.showToast("Please enter a valid email address!")
            } else if (currentUser.checkPhoneIsEmpty()) {
                context.showToast("Please enter a mobile number!")
            } else if (currentUser.checkPhoneIsNotValid()) {
                context.showToast("Please enter a valid 10 digit mobile number!")
            } else {
                disableEditView()
                viewModel.updateUser(currentUser)
            }
        }

        ModalBottomSheet(
            onDismissRequest = {
                disableEditView()
                hideBottomSheet()
            },
            sheetState = bottomSheetState,
        ) {
            val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.screen_padding)
            ) {
                OutlinedTextField(
                    value = currentUser.name ?: "",
                    onValueChange = { currentUser = currentUser.copy(name = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.screen_padding),
                    enabled = isEditable,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(R.string.email),
                            tint = AppTheme.colors.primary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.full_name),
                            modifier = Modifier.background(Color.Transparent)
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(R.string.enter_your_name))
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = Color.Black.copy(alpha = 0.10F),
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                )
                OutlinedTextField(
                    value = currentUser.email ?: "",
                    onValueChange = { currentUser = currentUser.copy(email = it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.inner_padding),
                    enabled = isEditable,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = stringResource(R.string.email),
                            tint = AppTheme.colors.primary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.email_address),
                            modifier = Modifier.background(Color.Transparent)
                        )
                    },
                    placeholder = { Text(text = stringResource(R.string.sample_email)) },
                    singleLine = true,
                    shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = Color.Black.copy(alpha = 0.10F),
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    ),
                )
                OutlinedTextField(
                    value = currentUser.phone ?: "",
                    onValueChange = {
                        if (it.matches(pattern) && it.length <= 10) {
                            currentUser = currentUser.copy(phone = it)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.inner_padding),
                    enabled = isEditable,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = stringResource(R.string.phone),
                            tint = AppTheme.colors.primary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.phone),
                            modifier = Modifier.background(Color.Transparent)
                        )
                    },
                    placeholder = { Text(text = stringResource(R.string.phone_placeholder)) },
                    singleLine = true,
                    shape = RoundedCornerShape(AppTheme.dimens.textfield_radius),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = Color.Black.copy(alpha = 0.10F),
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
                )
                Spacer(Modifier.height(AppTheme.dimens.inner_padding))
                if (!isEditable) {
                    Button(
                        onClick = { enableEditView() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colors.primary,
                            disabledContainerColor = AppTheme.colors.primary.copy(alpha = 0.25F),
                        ),
                        contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding),
                        shape = RoundedCornerShape(AppTheme.dimens.button_primary_radius)
                    ) {
                        Text(
                            text = "Edit",
                            textAlign = TextAlign.Center,
                            fontSize = AppTheme.fontDimens.bodyPrimary,
                            color = AppTheme.colors.textPrimary,
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.
                        fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                currentUser = user
                                disableEditView()
                            },
                            modifier = Modifier.weight(1F),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding),
                            shape = RoundedCornerShape(AppTheme.dimens.button_primary_radius)
                        ) {
                            Text(
                                text = "Cancel",
                                textAlign = TextAlign.Center,
                                fontSize = AppTheme.fontDimens.bodyPrimary,
                                color = AppTheme.colors.textPrimary,
                            )
                        }
                        Spacer(modifier = Modifier.width(AppTheme.dimens.inner_padding))
                        Button(
                            onClick = { checkEmailAndPhone() },
                            modifier = Modifier.weight(1F),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Green
                            ),
                            contentPadding = PaddingValues(AppTheme.dimens.button_inner_padding),
                            shape = RoundedCornerShape(AppTheme.dimens.button_primary_radius)
                        ) {
                            Text(
                                text = "Save",
                                textAlign = TextAlign.Center,
                                fontSize = AppTheme.fontDimens.bodyPrimary,
                                color = AppTheme.colors.textPrimary,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(AppTheme.dimens.inner_padding))
            }
        }
    }

    @Composable
    fun AlertDialogWindow(user: User) {
        BasicAlertDialog(
            onDismissRequest = { hideAlertDialog() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.colors.cardBackground
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.cardPadding),
                ) {
                    Text(
                        text = "Delete ${user.name}?",
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = AppTheme.fontDimens.titlePrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
                    Text(
                        text = "Are you sure to delete the selected user?",
                        fontSize = AppTheme.fontDimens.bodyPrimary,
                        minLines = 3,
                        maxLines = 3,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
                    Text(
                        text = "Confirm",
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { viewModel.deleteUser(user) },
                        fontSize = AppTheme.fontDimens.bodyPrimary,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            }
        }
    }

    if (isBottomSheetVisible) {
        BottomSheetWindow(user = selectedUser!!)
    }

    if (isAlertDialogVisible) {
        AlertDialogWindow(user = selectedUser!!)
    }

    BackgroundWithBottomNavigation(
        navController = navController,
        isAdminUser = true
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .matchParentSize()
                    .padding(AppTheme.dimens.screen_padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Users",
                    fontSize = AppTheme.fontDimens.headingPrimary,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.colors.textPrimary,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (userList.isEmpty()) {
                        Text(
                            text = "No users found",
                            modifier = Modifier
                                .matchParentSize()
                                .wrapContentHeight(),
                            fontSize = AppTheme.fontDimens.titleSecondary,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .matchParentSize(),
                            state = lazyListState,
                            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.inner_padding),
                        ) {
                            items(userList) { item ->
                                ItemUser(user = item)
                            }
                        }
                    }
                }
            }
            if (isFullScreenLoaderVisible) {
                FullScreenProgressLoader()
            }
        }
    }

    LaunchedEffect(isUserUpdated) {
        if (isUserUpdated == false) {
            context.showToast("Unable to update this user!")
        }

        if (isUserUpdated == true) {
            context.showToast("User updated successfully!")
            resetSelectedUser()
            hideBottomSheet()
        }

        viewModel.resetUserUpdated()
    }

    LaunchedEffect(isUserDeleted) {
        if (isUserDeleted == false) {
            context.showToast("Unable to delete this user!")
        }

        if (isUserDeleted == true) {
            context.showToast("User deleted successfully!")
            resetSelectedUser()
        }

        hideAlertDialog()
        viewModel.resetUserDeleted()
    }

}

@Preview(showSystemUi = true)
@Composable
fun UsersScreenPreview() {
    AppTheme {
        UsersScreen(
            navController = rememberNavController()
        )
    }
}