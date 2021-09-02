package com.example.cocktail.base.platform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    inline fun wrapBlockingOperation(crossinline apiCall: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch {
            apiCall()
        }
    }
}
