package org.doodoo.travel.data.travelguide.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TravelGuideResponse(
    @SerialName("_id")
    val id: String,
)