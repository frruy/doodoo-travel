package org.doodoo.travel.core.network.errors

enum class CustomError {
    ServiceUnavailable,
    ClientError,
    ServerError,
    UnknownError
}

class CustomException(error: CustomError): Exception(
    "Something goes wrong: $error"
)