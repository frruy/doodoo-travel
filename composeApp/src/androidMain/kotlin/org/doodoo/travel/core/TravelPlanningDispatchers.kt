package org.doodoo.travel.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.doodoo.travel.core.TravelPlanningDispatchers

actual val travelPlanningDispatchers: TravelPlanningDispatchers = object: TravelPlanningDispatchers {
    override val main: CoroutineDispatcher = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher = Dispatchers.IO
    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}