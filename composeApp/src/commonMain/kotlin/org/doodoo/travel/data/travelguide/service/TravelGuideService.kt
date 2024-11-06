package org.doodoo.travel.data.travelguide.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.doodoo.travel.core.network.errors.handleErrors
import org.doodoo.travel.data.travelguide.response.TravelGuideResponse

class TravelGuideService(
    private val httpClient: HttpClient
) {

    //Handle page size if needed
    suspend fun fetchWorkouts(): TravelGuideResponse {
        return handleErrors {
            httpClient.get("http://demo6732818.mockable.io/workouts") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}