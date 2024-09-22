package org.doodoo.travel.presentation.home

class DefaultHomeRepository : HomeRepository {
    override suspend fun getHomeData(): HomeData {
        // Implement your data fetching logic here
        return HomeData("Sample Data")
    }

    override suspend fun updateHomeData(newData: String): HomeData {
        // Implement your data updating logic here
        return HomeData(newData)
    }
}