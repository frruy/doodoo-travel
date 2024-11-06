package org.doodoo.travel.data.place.repository

import org.doodoo.travel.data.place.model.PlaceDetailResponse
import org.doodoo.travel.data.place.service.PlacesService

interface PlacesRepository {
    suspend fun getPlaceDetails(placeId: String): PlaceDetailResponse
}

class PlacesRepositoryImpl(
    private val placesService: PlacesService
) : PlacesRepository {
    override suspend fun getPlaceDetails(placeId: String): PlaceDetailResponse {
        return placesService.getPlaceDetails(placeId)
    }
}