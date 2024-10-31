package org.doodoo.travel.data.travelguide.repository

import org.doodoo.travel.data.travelguide.model.TravelGuide


interface TravelGuideRepository {
    suspend fun getData(input: String): TravelGuide
}