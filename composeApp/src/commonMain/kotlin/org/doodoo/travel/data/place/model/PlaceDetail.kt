package org.doodoo.travel.data.place.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetail(
  @SerialName("place_id")
  val placeId: String,
  val name: String,
  @SerialName("formatted_address")
  val formattedAddress: String,
  val rating: Double? = null,
  @SerialName("user_ratings_total")
  val userRatingsTotal: Int? = null,
  val photos: List<PlacePhoto>? = null,
  @SerialName("opening_hours")
  val openingHours: OpeningHours? = null,
  @SerialName("formatted_phone_number")
  val formattedPhoneNumber: String? = null,
  val website: String? = null,
  val reviews: List<Review>? = null,
  val geometry: Geometry? = null
)

@Serializable
data class PlacePhoto(
  @SerialName("photo_reference")
  val photoReference: String,
  val height: Int,
  val width: Int,
  @SerialName("html_attributions")
  val htmlAttributions: List<String>
)

@Serializable
data class OpeningHours(
  @SerialName("open_now")
  val openNow: Boolean? = null,
  val periods: List<Period>? = null,
  @SerialName("weekday_text")
  val weekdayText: List<String>? = null
)

@Serializable
data class Period(
  val open: TimeOfDay,
  val close: TimeOfDay? = null
)

@Serializable
data class TimeOfDay(
  val day: Int,
  val time: String
)

@Serializable
data class Review(
  @SerialName("author_name")
  val authorName: String,
  val rating: Int,
  @SerialName("relative_time_description")
  val relativeTimeDescription: String,
  val text: String,
  val time: Long
)

@Serializable
data class Geometry(
  val location: Location
)

@Serializable
data class Location(
  val lat: Double,
  val lng: Double
)