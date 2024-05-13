package com.abzer.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abzer.weatherapp.data.model.User
import com.abzer.weatherapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersVM @Inject constructor(
    private val userRepository: UserRepository
): BaseVM() {

    val isUserUpdated: LiveData<Boolean> = userRepository.isUserUpdated
    val isUserDeleted: LiveData<Boolean> = userRepository.isUserDeleted
    val users: LiveData<List<User>> = userRepository.users

    fun updateUser(user: User) {
        setLoading(true)
        userRepository.updateUser(user)
    }

    fun deleteUser(user: User) {
        setLoading(true)
        userRepository.deleteUser(user)
    }

    fun getUsers() {
        setLoading(true)
        userRepository.getUsers()
    }

    init {
        getUsers()
    }

}