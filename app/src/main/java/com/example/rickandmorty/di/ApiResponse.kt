package com.example.rickandmorty.di

import okhttp3.ResponseBody
import retrofit2.Response

sealed class ApiResponse<out T> {
    data class Progress<T>(var loading: Boolean) : ApiResponse<T>()
    data class Success<T>(var data: T) : ApiResponse<T>()
    data class Failure<T>(val e: Throwable,var data: Response<ResponseBody>?) : ApiResponse<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): ApiResponse<T> = Progress(isLoading)
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun <T> failure(e: Throwable,data : Response<ResponseBody>?): ApiResponse<T> = Failure(e,data)
    }
}
