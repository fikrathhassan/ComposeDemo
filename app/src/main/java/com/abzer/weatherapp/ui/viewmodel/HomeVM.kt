package com.abzer.weatherapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.util.DatastoreManager
import com.abzer.weatherapp.util.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val datastoreManager: DatastoreManager
): BaseVM() {

    private val _isLoggedOut = MutableLiveData(false)
    val isLoggedOut = _isLoggedOut

    fun logout() = viewModelScope.launch {
        datastoreManager.setLoggedIn(false)
        datastoreManager.setLoggedUser(Users.NONE)
        _isLoggedOut.postValue(true)
    }

}