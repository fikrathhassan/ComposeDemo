package com.abzer.weatherapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abzer.weatherapp.util.DatastoreManager

open class BaseVM: ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

}