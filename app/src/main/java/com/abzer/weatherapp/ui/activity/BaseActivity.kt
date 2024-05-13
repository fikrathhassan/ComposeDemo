package com.abzer.weatherapp.ui.activity

import androidx.activity.ComponentActivity
import com.abzer.weatherapp.util.DatastoreManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DatastoreManager

}