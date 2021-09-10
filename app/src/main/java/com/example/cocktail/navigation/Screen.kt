package com.example.cocktail.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val title: String, val icon: ImageVector) {
    Search("Search", Icons.Filled.Search),
    Filter("Filter", Icons.Filled.Filter);

    companion object {
        fun fromRoute(route: String?): Screen =
            when (route?.substringBefore("/")) {
                Search.name -> Search
                Filter.name -> Filter
                null -> Search
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
