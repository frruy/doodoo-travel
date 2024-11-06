package org.doodoo.travel.data.place.repository

import org.doodoo.travel.data.place.model.PlaceDetail
import org.doodoo.travel.data.place.service.PlacesService

interface PlacesRepository {
    suspend fun getPlaceDetails(placeId: String): PlaceDetail
}

class PlacesRepositoryImpl(
    private val placesService: PlacesService
) : PlacesRepository {
    override suspend fun getPlaceDetails(placeId: String): PlaceDetail {
        return placesService.getPlaceDetails(placeId)
    }
}