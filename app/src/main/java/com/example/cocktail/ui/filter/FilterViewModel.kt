package com.example.cocktail.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocktail.base.platform.BaseViewModel
import com.example.cocktail.base.platform.Result
import com.example.cocktail.data.repository.ICocktailRepository
import com.example.cocktail.ui.search.DrinksViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val repository: ICocktailRepository
) : BaseViewModel() {

    private val _categoriesViewState = MutableLiveData<CategoriesViewState>()
    val categoriesViewState: LiveData<CategoriesViewState> = _categoriesViewState

    private val _drinksViewState = MutableLiveData<DrinksViewState>()
    val drinksViewState: LiveData<DrinksViewState> = _drinksViewState

    fun getCategories() {
        _categoriesViewState.postValue(CategoriesViewState.Loading)
        wrapBlockingOperation {
            when (val result = repository.getCategories()) {
                is Result.Success -> {
                    _categoriesViewState.postValue(CategoriesViewState.Success(result.data))
                }
                is Result.Error -> {
                    _categoriesViewState.postValue(CategoriesViewState.Error(result.exception.errorMessage!!))
                }
            }
        }
    }

    fun filterCocktailsByCategory(category: String) {
        _drinksViewState.postValue(DrinksViewState.Loading)
        wrapBlockingOperation {
            when (val result = repository.filterCocktailsByCategory(category)) {
                is Result.Success -> {
                    _drinksViewState.postValue(DrinksViewState.Success(result.data))
                }
                is Result.Error -> {
                    _drinksViewState.postValue(DrinksViewState.Error(result.exception.errorMessage!!))
                }
            }
        }
    }
}

sealed class CategoriesViewState {

    object Loading : CategoriesViewState()

    data class Success(val categories: List<String>) : CategoriesViewState()

    data class Error(val errorMessage: String) : CategoriesViewState()
}
