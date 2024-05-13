package com.abzer.weatherapp.di

import android.content.Context
import com.abzer.weatherapp.util.DatastoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatastoreManager(@ApplicationContext context: Context): DatastoreManager {
        return DatastoreManager(context)
    }

}