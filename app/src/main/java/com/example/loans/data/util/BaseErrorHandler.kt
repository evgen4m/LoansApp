package com.example.loans.data.util

import com.example.loans.domain.entities.ErrorEntity
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class BaseErrorHandler @Inject constructor() {

     fun get(throwable: Throwable): ErrorEntity =
        when(throwable) {
            is IOException -> ErrorEntity.Network
            is HttpException -> {
                when(throwable.code()) {
                    // not found
                    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound

                    // bad request
                    HttpURLConnection.HTTP_BAD_REQUEST -> ErrorEntity.BadRequest

                    // access denied
                    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied

                    // unavailable service
                    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable

                    // unauthorized
                    HttpURLConnection.HTTP_UNAUTHORIZED -> ErrorEntity.Unauthorized

                    // all the others will be treated as unknown error
                    else -> ErrorEntity.Unknown
                }
            }
            else -> ErrorEntity.Unknown
        }


}