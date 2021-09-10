package com.example.cocktail.data.api

import com.example.cocktail.entity.DrinksResponse
import com.example.cocktail.entity.FilterCategoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("search.php")
    suspend fun searchCocktailsByName(
        @Query("s") name: String
    ): Response<DrinksResponse>

    @GET("filter.php")
    suspend fun filterCocktailsByCategory(
        @Query("c") category: String
    ): Response<DrinksResponse>

    @GET("list.php?c=list")
    suspend fun getCategories(): Response<FilterCategoriesResponse>
}
