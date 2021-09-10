package com.example.cocktail.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cocktail.ui.filter.FilterScreen
import com.example.cocktail.ui.search.SearchScreen

@ExperimentalFoundationApi
@Composable
fun CocktailNavHost(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Search.name) {
            SearchScreen()
        }

        composable(Screen.Filter.name) {
            FilterScreen()
        }
    }
}
