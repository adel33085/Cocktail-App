package com.example.cocktail.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktail.R
import com.example.cocktail.base.ui.ErrorScreen
import com.example.cocktail.base.ui.LoadingScreen
import com.example.cocktail.domain.Drink
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.searchCocktailsByName("margarita")
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        var cocktailName by remember { mutableStateOf("margarita") }

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            val (searchTextField, searchButton) = createRefs()
            TextField(
                value = cocktailName,
                onValueChange = { cocktailName = it },
                maxLines = 1,
                singleLine = true,
                label = { Text(text = "enter cocktail name") },
                modifier = Modifier.constrainAs(searchTextField) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(searchButton.start)
                    width = Dimension.fillToConstraints
                }
            )

            Button(
                onClick = { viewModel.searchCocktailsByName(cocktailName) },
                modifier = Modifier.constrainAs(searchButton) {
                    top.linkTo(searchTextField.top)
                    bottom.linkTo(searchTextField.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
            ) {
                Text(text = "Search")
            }
        }

        when (val drinksViewState = viewModel.drinksViewState.observeAsState().value) {
            is DrinksViewState.Loading -> {
                LoadingScreen()
            }
            is DrinksViewState.Success -> {
                val drinks = drinksViewState.drinks
                if (drinks.isEmpty().not()) {
                    DrinksScreen(drinks = drinks)
                } else {
                    EmptyScreen()
                }
            }
            is DrinksViewState.Error -> {
                ErrorScreen(
                    errorMessage = drinksViewState.errorMessage,
                    retry = { viewModel.searchCocktailsByName(cocktailName) }
                )
            }
        }
    }
}

@Composable
fun EmptyScreen(message: String = "No Result Found !") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun DrinksScreen(drinks: List<Drink>) {
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(drinks) {
            DrinkListItem(drink = it)
        }
    }
}

@Composable
fun DrinkListItem(drink: Drink) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        GlideImage(
            imageModel = drink.thumb,
            contentScale = ContentScale.Fit,
            circularReveal = CircularReveal(),
            error = painterResource(id = R.drawable.ic_baseline_broken_image_24),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .height(150.dp)
        )
        Text(
            text = drink.name,
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}
