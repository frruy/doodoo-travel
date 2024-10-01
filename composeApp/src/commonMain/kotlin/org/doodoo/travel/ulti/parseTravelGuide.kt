package org.doodoo.travel.ulti

import kotlinx.serialization.json.Json
import org.doodoo.travel.core.model.TravelGuide

fun parseTravelGuide(jsonString: String): TravelGuide {
       return Json.decodeFromString(jsonString)
   }