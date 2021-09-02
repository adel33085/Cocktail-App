package com.example.cocktail.base.platform

import com.example.cocktail.base.utils.IConnectivityUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var connectivityUtils: IConnectivityUtils

    private val noInternetError = Result.Error(
        ApplicationException(
            type = ErrorType.Network.NoInternetConnection,
            errorMessage = "no internet connection"
        )
    )

    private val unexpectedError = Result.Error(
        ApplicationException(
            type = ErrorType.Network.Unexpected,
            errorMessage = "something went wrong"
        )
    )

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                if (connectivityUtils.isNetworkConnected.not()) {
                    return@withContext noInternetError
                }
                val response = call()
                return@withContext handleResult(response)
            } catch (error: Throwable) {
                Timber.e(error)
                unexpectedError
            }
        }
    }

    private fun <T : Any> handleResult(response: Response<T>): Result<T> {
        return when (response.code()) {
            200 -> {
                Result.Success(response.body()!!)
            }
            401 -> {
                Result.Error(
                    ApplicationException(
                        type = ErrorType.Network.Unauthorized,
                        errorMessage = "unauthorized"
                    )
                )
            }
            404 -> {
                Result.Error(
                    ApplicationException(
                        type = ErrorType.Network.ResourceNotFound,
                        errorMessage = "resource not found"
                    )
                )
            }
            else -> {
                unexpectedError
            }
        }
    }
}
