package org.doodoo.travel.data.repository

import org.doodoo.travel.core.model.DiscoverData


interface DiscoverRepository {
    suspend fun getData(input: String): DiscoverData
}