package com.abzer.weatherapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.abzer.weatherapp.data.dao.UserDao
import com.abzer.weatherapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    private val _coroutineScope = CoroutineScope(Dispatchers.IO)
    val isUserRegistered = MutableLiveData<Boolean>()
    val isUserAdded = MutableLiveData<Boolean>()
    val isUserUpdated = MutableLiveData<Boolean>()
    val isUserDeleted = MutableLiveData<Boolean>()
    val user = MutableLiveData<User>()
    val users = MutableLiveData<List<User>>()

    fun addUser(user: User) {
        _coroutineScope.launch {
            userDao.addUser(user)
            isUserAdded.postValue(true)
        }
    }

    fun findUserByEmail(email: String) {
        _coroutineScope.launch {
            val result = userDao.findUserByEmail(email)
            isUserRegistered.postValue(result != null)
        }
    }

    fun findUser(email: String, password: String) {
        _coroutineScope.launch {
            user.postValue(userDao.findUser(email, password))
        }
    }

    fun updateUser(user: User) {
        _coroutineScope.launch {
            userDao.updateUser(user)
            isUserUpdated.postValue(true)
        }
    }

    fun deleteUser(user: User) {
        _coroutineScope.launch {
            userDao.deleteUser(user)
            isUserDeleted.postValue(true)
        }
    }

    fun getUsers() {
        _coroutineScope.launch {
            users.postValue(userDao.getUsers())
        }
    }

}