package com.example.cocktail.data.repository

import com.example.cocktail.base.platform.BaseRepository
import com.example.cocktail.base.platform.Result
import com.example.cocktail.data.api.CocktailApi
import com.example.cocktail.domain.Drink
import com.example.cocktail.entity.toDrink
import javax.inject.Inject

class CocktailRepository @Inject constructor(
    private val cocktailApi: CocktailApi
) : BaseRepository(), ICocktailRepository {

    override suspend fun searchCocktailsByName(name: String): Result<List<Drink>> {
        val result = safeApiCall {
            cocktailApi.searchCocktailsByName(name)
        }
        return when (result) {
            is Result.Success -> {
                val drinks = result.data.drinks?.mapNotNull { it.toDrink() } ?: emptyList()
                Result.Success(drinks)
            }
            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun filterCocktailsByCategory(category: String): Result<List<Drink>> {
        val result = safeApiCall {
            cocktailApi.filterCocktailsByCategory(category)
        }
        return when (result) {
            is Result.Success -> {
                val drinks = result.data.drinks?.mapNotNull { it.toDrink() } ?: emptyList()
                Result.Success(drinks)
            }
            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun getCategories(): Result<List<String>> {
        val result = safeApiCall {
            cocktailApi.getCategories()
        }
        return when (result) {
            is Result.Success -> {
                val categories = result.data.Categories?.mapNotNull { it.category } ?: emptyList()
                Result.Success(categories)
            }
            is Result.Error -> {
                result
            }
        }
    }
}
