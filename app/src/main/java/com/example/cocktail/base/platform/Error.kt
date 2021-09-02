package com.example.cocktail.base.platform

class ApplicationException(
    val type: ErrorType,
    val errorMessage: String? = null,
    val errorMessageRes: Int? = null,
    val throwable: Throwable? = null
) : RuntimeException()

sealed class ErrorType {
    sealed class Network : ErrorType() {
        object Unauthorized : Network()
        object ResourceNotFound : Network()
        object Unexpected : Network()
        object NoInternetConnection : Network()
    }

    object Unexpected : ErrorType()
}
