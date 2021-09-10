package com.example.cocktail.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cocktail.base.platform.BaseViewModel
import com.example.cocktail.base.platform.Result
import com.example.cocktail.data.repository.ICocktailRepository
import com.example.cocktail.domain.Drink
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ICocktailRepository
) : BaseViewModel() {

    private val _drinksViewState = MutableLiveData<DrinksViewState>()
    val drinksViewState: LiveData<DrinksViewState> = _drinksViewState

    fun searchCocktailsByName(name: String) {
        _drinksViewState.postValue(DrinksViewState.Loading)
        wrapBlockingOperation {
            when (val result = repository.searchCocktailsByName(name)) {
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

sealed class DrinksViewState {

    object Loading : DrinksViewState()

    data class Success(val drinks: List<Drink>) : DrinksViewState()

    data class Error(val errorMessage: String) : DrinksViewState()
}
