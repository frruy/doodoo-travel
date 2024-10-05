package com.mocoding.pokedex

import kotlinx.coroutines.CoroutineDispatcher

interface TravelPlanningDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}

expect val travelPlanningDispatchers: TravelPlanningDispatchers