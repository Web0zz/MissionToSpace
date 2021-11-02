package com.web0zz.domain.exception

sealed class Failure {
    data class UnknownError(val message: String) : Failure()
    data class NetworkConnectionError(val message: String) : Failure()
    data class ApiResponseError(val message: String) : Failure()
}
