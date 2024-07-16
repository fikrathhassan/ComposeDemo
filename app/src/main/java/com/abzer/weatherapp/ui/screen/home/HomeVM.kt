package com.abzer.weatherapp.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.data.repository.datastore.DataStoreRepository
import com.abzer.weatherapp.ui.BaseVM
import com.abzer.weatherapp.util.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): BaseVM() {

    val loggedUser = dataStoreRepository.loggedUser

    private val _isLoggedOut = MutableStateFlow(false)
    val isLoggedOut = _isLoggedOut.asStateFlow()
    private fun setLoggedOut() {
        _isLoggedOut.value = true
    }

    private fun setSignedIn() {
        viewModelScope.launch {
            dataStoreRepository.setStaySignedIn(false)
        }
    }

    private fun setLoggedUser() {
        viewModelScope.launch {
            dataStoreRepository.setLoggedUser(Users.USER)
        }
    }

    fun logout() {
        viewModelScope.launch {
            setLoading(true)
            setSignedIn()
            setLoggedUser()
            setLoading(false)
            setLoggedOut()
        }
    }

}