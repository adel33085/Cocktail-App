package com.example.cocktail.base.di

import com.example.cocktail.base.utils.ConnectivityUtils
import com.example.cocktail.base.utils.IConnectivityUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MainModule {

    @Binds
    abstract fun bindConnectivityUtils(connectivityUtils: ConnectivityUtils): IConnectivityUtils
}
