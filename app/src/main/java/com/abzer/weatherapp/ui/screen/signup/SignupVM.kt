package com.abzer.weatherapp.ui.screen.signup

import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.user.UserRepository
import com.abzer.weatherapp.ui.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(
    private val userRepository: UserRepository
): BaseVM() {

    private val _isUserRegistered = MutableStateFlow<Boolean?>(null)
    val isUserRegistered = _isUserRegistered.asStateFlow()
    fun isUserExist(email: String) {
        setLoading(true)
        viewModelScope.launch {
            val user = async(Dispatchers.IO) {
                userRepository.isUserExist(email)
            }.await()
            _isUserRegistered.value = user != null
            setLoading(false)
        }
    }
    fun resetUserRegistered() {
        _isUserRegistered.value = null
    }

    private val _isUserAdded = MutableStateFlow<Boolean?>(null)
    val isUserAdded = _isUserAdded.asStateFlow()
    fun addUser(user: User) {
        setLoading(true)
        viewModelScope.launch {
            val userId = async(Dispatchers.IO) {
                userRepository.addOrUpdateUser(user)
            }.await()
            _isUserAdded.value = userId > 0
            setLoading(false)
        }
    }
    fun resetUserAdded() {
        _isUserAdded.value = null
    }

}