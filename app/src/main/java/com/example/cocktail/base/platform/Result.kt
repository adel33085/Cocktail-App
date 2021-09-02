package com.example.cocktail.base.platform

sealed class Result<out R> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(val exception: ApplicationException) : Result<Nothing>()
}
