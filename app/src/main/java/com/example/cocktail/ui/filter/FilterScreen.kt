package com.example.cocktail.ui.filter

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cocktail.R
import com.example.cocktail.base.ui.ErrorScreen
import com.example.cocktail.base.ui.LoadingScreen
import com.example.cocktail.ui.search.DrinksScreen
import com.example.cocktail.ui.search.DrinksViewState
import com.example.cocktail.ui.search.EmptyScreen

@ExperimentalFoundationApi
@Composable
fun FilterScreen(viewModel: FilterViewModel = hiltViewModel()) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Select Category") }
    var dropdownWidth by remember { mutableStateOf(Size.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.LightGray)
                .onGloballyPositioned { coordinates ->
                    dropdownWidth = coordinates.size.toSize()
                }
                .clickable {
                    viewModel.getCategories()
                    expanded = true
                }
            ) {
                val (selectedCategoryText, arrowIconImage) = createRefs()
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_24),
                    contentDescription = null,
                    modifier = Modifier.constrainAs(arrowIconImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 16.dp)
                    }
                )
                Text(
                    text = selectedCategory,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(selectedCategoryText) {
                            top.linkTo(arrowIconImage.top)
                            bottom.linkTo(arrowIconImage.bottom)
                            start.linkTo(parent.start, margin = 16.dp)
                            end.linkTo(arrowIconImage.start, margin = 16.dp)
                            width = Dimension.fillToConstraints
                        }
                )
            }

            when (val categoriesViewState = viewModel.categoriesViewState.observeAsState().value) {
                is CategoriesViewState.Loading -> {
                    LoadingScreen()
                }
                is CategoriesViewState.Success -> {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { dropdownWidth.width.toDp() })
                            .height(250.dp)
                    ) {
                        categoriesViewState.categories.forEach {
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.filterCocktailsByCategory(it)
                                    selectedCategory = it
                                    expanded = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = it, textAlign = TextAlign.Start, maxLines = 1)
                            }
                        }
                    }
                }
                is CategoriesViewState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        categoriesViewState.errorMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        when (val drinksViewState = viewModel.drinksViewState.observeAsState().value) {
            is DrinksViewState.Loading -> {
                LoadingScreen()
            }
            is DrinksViewState.Success -> {
                val drinks = drinksViewState.drinks
                if (drinks.isEmpty().not()) {
                    DrinksScreen(drinks = drinksViewState.drinks)
                } else {
                    EmptyScreen()
                }
            }
            is DrinksViewState.Error -> {
                ErrorScreen(
                    errorMessage = drinksViewState.errorMessage,
                    retry = { viewModel.filterCocktailsByCategory(selectedCategory) }
                )
            }
        }
    }
}
