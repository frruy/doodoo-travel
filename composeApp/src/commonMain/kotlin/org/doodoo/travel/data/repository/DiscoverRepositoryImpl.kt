package org.doodoo.travel.data.repository

import io.ktor.client.*
import kotlinx.coroutines.delay
import org.doodoo.travel.core.model.DiscoverData

class DiscoverRepositoryImpl(private val httpClient: HttpClient) : DiscoverRepository {
    override suspend fun getData(input: String): DiscoverData {
        // Implement API call using httpClient
        // For example:
        delay(3000)
        return DiscoverData("Hello world: $input")
//        return httpClient.get("https://api.example.com/data").body()
    }
}