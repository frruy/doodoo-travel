package org.doodoo.travel.presentation.home

data class HomeData(val someData: String)

interface HomeRepository {
    suspend fun getHomeData(): HomeData
    suspend fun updateHomeData(newData: String): HomeData
}