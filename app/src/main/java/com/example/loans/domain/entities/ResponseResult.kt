package com.example.loans.domain.entities

sealed class ResponseResult<out T> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val error: ErrorEntity) : ResponseResult<Nothing>()
    data class LocalData<out T>(val localData: T, val error: ErrorEntity.Network) : ResponseResult<T>()
}
