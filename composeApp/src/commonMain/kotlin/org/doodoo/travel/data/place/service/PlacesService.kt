package org.doodoo.travel.data.place.service

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.doodoo.travel.core.network.errors.handleErrors
import org.doodoo.travel.data.place.model.PlaceDetail

class PlacesService(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place"
    }

    suspend fun getPlaceDetails(placeId: String): PlaceDetail {
        return handleErrors {
            httpClient.get("$BASE_URL/details/json") {
                contentType(ContentType.Application.Json)
                url {
                    parameters.append("place_id", placeId)
                    parameters.append("key", apiKey)
                    parameters.append("fields", "name,formatted_address,rating,user_ratings_total,photos,opening_hours,formatted_phone_number,website,reviews,geometry")
                }
            }
        }
    }
}