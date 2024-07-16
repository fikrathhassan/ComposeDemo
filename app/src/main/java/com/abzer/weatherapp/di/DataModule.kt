package com.abzer.weatherapp.di

import com.abzer.weatherapp.data.repository.datastore.DataStoreRepository
import com.abzer.weatherapp.data.repository.datastore.DataStoreRepositoryImpl
import com.abzer.weatherapp.data.repository.user.UserRepository
import com.abzer.weatherapp.data.repository.user.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindDataStoreRepository(dataStoreRepositoryImpl: DataStoreRepositoryImpl): DataStoreRepository


}