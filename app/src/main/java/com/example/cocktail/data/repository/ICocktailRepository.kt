package com.example.cocktail.data.repository

import com.example.cocktail.base.platform.Result
import com.example.cocktail.domain.Drink

interface ICocktailRepository {

    suspend fun searchCocktailsByName(name: String): Result<List<Drink>>

    suspend fun filterCocktailsByCategory(category: String): Result<List<Drink>>

    suspend fun getCategories(): Result<List<String>>
}
