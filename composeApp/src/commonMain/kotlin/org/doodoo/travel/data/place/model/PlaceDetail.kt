package org.doodoo.travel.data.place.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailResponse(
    val html_attributions: List<String> = emptyList(), val result: PlaceDetail, val status: String
)

@Serializable
data class PlaceDetail(
    val address_components: List<AddressComponent>,
    val adr_address: String,
    val business_status: String,
    val current_opening_hours: OpeningHours? = null,
    val formatted_address: String,
    val formatted_phone_number: String? = null,
    val geometry: Geometry,
    val icon: String,
    val icon_background_color: String,
    val icon_mask_base_uri: String,
    val international_phone_number: String? = null,
    val name: String,
    val opening_hours: OpeningHours? = null,
    val photos: List<Photo>? = null,
    val place_id: String,
    val plus_code: PlusCode? = null,
    val rating: Double? = null,
    val reference: String,
    val reviews: List<Review>? = null,
    val types: List<String>,
    val url: String,
    val user_ratings_total: Int? = null,
    val utc_offset: Int? = null,
    val vicinity: String,
    val website: String? = null,
    val wheelchair_accessible_entrance: Boolean? = null
)

@Serializable
data class AddressComponent(
    val long_name: String, val short_name: String, val types: List<String>
)

@Serializable
data class OpeningHours(
    val open_now: Boolean, val periods: List<Period>, val weekday_text: List<String>
)

@Serializable
data class Period(
    val close: TimePeriod, val open: TimePeriod
)

@Serializable
data class TimePeriod(
    val date: String? = null, val day: Int, val time: String
)

@Serializable
data class Geometry(
    val location: Location, val viewport: Viewport
)

@Serializable
data class Location(
    val lat: Double, val lng: Double
)

@Serializable
data class Viewport(
    val northeast: Location, val southwest: Location
)

@Serializable
data class Photo(
    val height: Int,
    val html_attributions: List<String>,
    val photo_reference: String,
    val width: Int
)

@Serializable
data class PlusCode(
    val compound_code: String, val global_code: String
)

@Serializable
data class Review(
    val author_name: String,
    val author_url: String,
)