package com.example.cocktail.di

import com.example.cocktail.data.api.CocktailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Provides
    fun provideCocktailApi(retrofit: Retrofit): CocktailApi {
        return retrofit.create(CocktailApi::class.java)
    }
}
