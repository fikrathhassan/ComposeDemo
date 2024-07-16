package com.abzer.weatherapp.ui.activity.main

import androidx.lifecycle.viewModelScope
import com.abzer.weatherapp.data.repository.datastore.DataStoreRepository
import com.abzer.weatherapp.ui.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainAVM @Inject constructor(
    dataStoreRepository: DataStoreRepository
): BaseVM() {

    companion object {
        private const val SPLASH_SCREEN_DURATION = 2_000L
    }

    private val _isSplashScreenVisible = MutableStateFlow(true)
    val isSplashScreenVisible = _isSplashScreenVisible.asStateFlow()
    private fun hideSplashScreenVisibility() {
        _isSplashScreenVisible.value = false
    }

    val isStayLoggedIn = dataStoreRepository.isStaySignedIn

    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_DURATION)
            hideSplashScreenVisibility()
        }
    }

}