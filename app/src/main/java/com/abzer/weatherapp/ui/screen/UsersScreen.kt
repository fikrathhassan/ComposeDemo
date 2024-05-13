package com.abzer.weatherapp.ui.screen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abzer.weatherapp.R
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.ui.theme.AppTheme
import com.abzer.weatherapp.ui.viewmodel.UsersVM
import com.abzer.weatherapp.util.CommonFunctions
import com.abzer.weatherapp.util.Extensions.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    navController: NavController,
    viewModel: UsersVM = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val lazyListState = rememberLazyListState()
    val usersList: MutableList<User> = remember { mutableStateListOf() }
    var selectedUser by remember { mutableStateOf(User()) }
    var isFullScreenLoaderVisible by remember { mutableStateOf(false) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isAlertDialogVisible by remember { mutableStateOf(false) }

    fun showLoader() { isFullScreenLoaderVisible = true }
    fun hideLoader() { isFullScreenLoaderVisible = false }

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
                        text = user.name ?: "N/A",
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
        val scope = rememberCoroutineScope()
        var currentUser by remember { mutableStateOf(user) }
        var isEditable by remember { mutableStateOf(false) }
        var isLoading = false

        fun showLoader() { isLoading = true }
        fun hideLoader() { isLoading = false }

        fun enableEditView() { isEditable = true }
        fun disableEditView() { isEditable = false }

        fun checkEmailAndPhone() {
            if (!currentUser.checkNameIsNotEmpty()) {
                context.showToast("Please enter a name!")
            } else if (!currentUser.checkEmailIsNotEmpty()) {
                context.showToast("Please enter an email address!")
            } else if (!currentUser.checkEmailIsValid()) {
                context.showToast("Please enter a valid email address!")
            } else if (!currentUser.checkPhoneIsNotEmpty()) {
                context.showToast("Please enter a mobile number!")
            } else if (!currentUser.checkPhoneIsValid()) {
                context.showToast("Please enter a valid 10 digit mobile number!")
            } else {
                showLoader()
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        scope.launch {
                            bottomSheetState.hide()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                isBottomSheetVisible = false
                            }
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close"
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
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
                    if (isLoading) {
                        ProgressLoader()
                    } else {
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
                    }
                    Spacer(Modifier.height(AppTheme.dimens.inner_padding))
                }
            }
        }
    }

    @Composable
    fun AlertDialogWindow(user: User) {
        val scroll = rememberScrollState(0)

        BasicAlertDialog(
            onDismissRequest = { hideAlertDialog() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scroll),
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

    CommonFunctions.printLog("UsersScreen")

    if (isBottomSheetVisible) {
        BottomSheetWindow(user = selectedUser)
    }

    if (isAlertDialogVisible) {
        AlertDialogWindow(user = selectedUser)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.screen_padding),
        ) {
            Text(
                text = "Users",
                fontSize = AppTheme.fontDimens.headingPrimary,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colors.textPrimary,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(AppTheme.dimens.inner_padding))
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.inner_padding),
                ) {
                    items(usersList) { item ->
                        ItemUser(user = item)
                    }
                }
                if (usersList.isEmpty()) {
                    Text(
                        text = "No users found",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(),
                        fontSize = AppTheme.fontDimens.titleSecondary,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
        if (isFullScreenLoaderVisible) {
            FullScreenProgressLoader()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.isLoading.observe(lifecycleOwner) {
            if (it) {
                showLoader()
            } else {
                hideLoader()
            }
        }
        viewModel.users.observe(lifecycleOwner) {
            usersList.clear()
            usersList.addAll(it)
            viewModel.setLoading(false)
        }
        viewModel.isUserUpdated.observe(lifecycleOwner) {
            context.showToast("User updated successfully!")
            viewModel.setLoading(false)
            selectedUser = User()
            hideBottomSheet()
            viewModel.getUsers()
        }
        viewModel.isUserDeleted.observe(lifecycleOwner) {
            context.showToast("User deleted successfully!")
            viewModel.setLoading(false)
            selectedUser = User()
            hideAlertDialog()
            viewModel.getUsers()
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun UsersScreenPreview() {
//    AppTheme {
//        UsersScreen(
//            navController = rememberNavController(),
//            viewModel =  null
//        )
//    }
//}