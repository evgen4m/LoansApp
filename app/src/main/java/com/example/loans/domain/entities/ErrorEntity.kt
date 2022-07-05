package com.example.loans.domain.entities

sealed class ErrorEntity {

    object BadRequest : ErrorEntity()

    object Network : ErrorEntity()

    object NotFound : ErrorEntity()

    object AccessDenied : ErrorEntity()

    object ServiceUnavailable : ErrorEntity()

    object Unauthorized: ErrorEntity()

    object Unknown : ErrorEntity()

}
