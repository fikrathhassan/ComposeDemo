package com.abzer.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.UserRepository
import com.abzer.weatherapp.util.DatastoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupVM @Inject constructor(
    private val userRepository: UserRepository
): BaseVM() {

    val isUserRegistered: LiveData<Boolean> = userRepository.isUserRegistered
    val user: LiveData<User> = userRepository.user
    val isUserAdded: LiveData<Boolean> = userRepository.isUserAdded

    fun addUser(user: User) {
        userRepository.addUser(user)
    }

    fun findUserByEmail(email: String) {
        userRepository.findUserByEmail(email)
    }

}