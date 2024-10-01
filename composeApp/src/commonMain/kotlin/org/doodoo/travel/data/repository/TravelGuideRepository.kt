package org.doodoo.travel.data.repository

import org.doodoo.travel.core.model.TravelGuide


interface TravelGuideRepository {
    suspend fun getData(input: String): TravelGuide
}