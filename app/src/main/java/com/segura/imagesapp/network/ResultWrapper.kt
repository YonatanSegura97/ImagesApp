package com.segura.imagesapp.network

import com.google.gson.Gson
import com.segura.imagesapp.model.ErrorResponse
import com.segura.imagesapp.utils.logErrorDebug
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class ApiError(
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {

    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            logErrorDebug("Error", throwable.toString(), throwable)
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is SocketTimeoutException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.ApiError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.NetworkError
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val gson = Gson()

            return gson.fromJson(it.toString(), ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}