package com.example.cocktail.di

import com.example.cocktail.data.repository.CocktailRepository
import com.example.cocktail.data.repository.ICocktailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindCocktailRepository(impl: CocktailRepository): ICocktailRepository
}
