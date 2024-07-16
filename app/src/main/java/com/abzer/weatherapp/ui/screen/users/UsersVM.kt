package com.abzer.weatherapp.ui.screen.users

import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.user.UserRepository
import com.abzer.weatherapp.ui.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersVM @Inject constructor(
    private val userRepository: UserRepository
): BaseVM() {

    private val _isUserUpdated = MutableStateFlow<Boolean?>(null)
    val isUserUpdated = _isUserUpdated.asStateFlow()
    fun updateUser(user: User) {
        viewModelScope.launch {
            val userId = async(Dispatchers.IO) {
                userRepository.addOrUpdateUser(user)
            }.await()
            _isUserUpdated.value = userId == user.id
        }
    }
    fun resetUserUpdated() {
        _isUserDeleted.value = null
    }

    private val _isUserDeleted = MutableStateFlow<Boolean?>(null)
    val isUserDeleted = _isUserDeleted.asStateFlow()
    fun deleteUser(user: User) {
        viewModelScope.launch {
            val numOfDeletion = async(Dispatchers.IO) {
                userRepository.deleteUser(user)
            }.await()
            _isUserDeleted.value = numOfDeletion == 1
        }
    }
    fun resetUserDeleted() {
        _isUserDeleted.value = null
    }

    val users: Flow<List<User>> = userRepository.getAllUsers()

}