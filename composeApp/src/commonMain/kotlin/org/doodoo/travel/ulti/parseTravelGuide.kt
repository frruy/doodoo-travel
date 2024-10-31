package org.doodoo.travel.ulti

import kotlinx.serialization.json.Json
import org.doodoo.travel.data.travelguide.model.TravelGuide

fun parseTravelGuide(jsonString: String): TravelGuide {
       return Json.decodeFromString(jsonString)
   }