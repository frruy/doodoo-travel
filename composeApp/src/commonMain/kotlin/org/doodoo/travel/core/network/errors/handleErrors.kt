package org.doodoo.travel.core.network.errors

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.withContext
import org.doodoo.travel.core.travelPlanningDispatchers

suspend inline fun <reified T> handleErrors(
    crossinline response: suspend () -> HttpResponse
): T = withContext(travelPlanningDispatchers.io) {

    val result = try {
        response()
    } catch(e: IOException) {
        throw CustomException(CustomError.ServiceUnavailable)
    }

    when(result.status.value) {
        in 200..299 -> Unit
        in 400..499 -> throw CustomException(CustomError.ClientError)
        500 -> throw CustomException(CustomError.ServerError)
        else -> throw CustomException(CustomError.UnknownError)
    }

    return@withContext try {
        result.body()
    } catch(e: Exception) {
        throw CustomException(CustomError.ServerError)
    }

}