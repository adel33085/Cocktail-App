package com.example.cocktail.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocktail.navigation.CocktailNavHost
import com.example.cocktail.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailApp()
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun CocktailApp() {
    val navController = rememberNavController()
    val backstackEntry = navController.currentBackStackEntryAsState()
    val screens = listOf(Screen.Search, Screen.Filter)
    val currentScreen = Screen.fromRoute(
        backstackEntry.value?.destination?.route
    )
    Scaffold(
        bottomBar = {
            CocktailTabRow(
                screens = screens,
                currentScreen = currentScreen,
                onTabSelected = { screen ->
                    navController.navigate(screen.name)
                }
            )
        }
    ) { innerPadding ->
        CocktailNavHost(
            navController = navController,
            startDestination = Screen.Search.name,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun CocktailTabRow(
    screens: List<Screen>,
    currentScreen: Screen,
    onTabSelected: (Screen) -> Unit
) {
    var tabIndex by remember { mutableStateOf(0) }
    TabRow(selectedTabIndex = tabIndex) {
        screens.forEach {
            LeadingIconTab(
                text = { Text(it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                selected = currentScreen == it,
                onClick = {
                    onTabSelected(it)
                    tabIndex = screens.indexOf(it)
                }
            )
        }
    }
}
