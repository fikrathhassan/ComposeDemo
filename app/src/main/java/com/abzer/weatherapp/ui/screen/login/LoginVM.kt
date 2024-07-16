package com.abzer.weatherapp.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.datastore.DataStoreRepository
import com.abzer.weatherapp.data.repository.user.UserRepository
import com.abzer.weatherapp.ui.BaseVM
import com.abzer.weatherapp.util.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val userRepository: UserRepository
): BaseVM() {

    fun setLoggedUser(user: Users) {
        viewModelScope.launch {
            dataStoreRepository.setLoggedUser(user)
        }
    }

    fun setStaySignedIn(isStaySignedIn: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.setStaySignedIn(isStaySignedIn)
        }
    }

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()
    fun setLoggedIn() {
        _isLoggedIn.value = true
    }

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
    fun resetIsRegisteredUser() {
        _isUserRegistered.value = null
    }

    private val _isIncorrectCredentials = MutableStateFlow(false)
    val isIncorrectCredentials = _isIncorrectCredentials.asStateFlow()
    fun resetIsIncorrectCredentials() {
        _isIncorrectCredentials.value = false
    }
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    fun findUserByPassword(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            val user = async(Dispatchers.IO) {
                userRepository.findUserByPassword(email, password)
            }.await()
            if (user == null) {
                _isIncorrectCredentials.value = true
            } else {
                _user.value = user
            }
            setLoading(false)
        }
    }

}