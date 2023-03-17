package com.example.rickandmorty.utils

import com.example.rickandmorty.di.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Error

fun<T> result(call: suspend() -> Response<T>) : Flow<ApiResponse<T?>> = flow {
    emit(ApiResponse.loading(true))

    try {
        val c = call()
        c.let {
            if (c.isSuccessful){
                emit(ApiResponse.success(c.body()))
            }else{
                c.errorBody()?.let {
                    it.close()
                    emit(ApiResponse.failure(Error(it.toString()), Response.error(400,c.errorBody())))

                }

            }
        }
    }catch(t:Throwable){
        t.printStackTrace()
        emit(ApiResponse.failure(t,null))
    }
}