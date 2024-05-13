package com.abzer.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(
    private val userRepository: UserRepository
): BaseVM() {

    val isUserRegistered: LiveData<Boolean> = userRepository.isUserRegistered
    val user: LiveData<User> = userRepository.user

    fun findUserByEmail(email: String) {
        userRepository.findUserByEmail(email)
    }

    fun findUser(email: String, password: String) {
        userRepository.findUser(email, password)
    }

}