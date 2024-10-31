package org.doodoo.travel.data.travelguide.model

import kotlinx.serialization.Serializable

@Serializable
data class TravelGuide(
    val destination: String,
    val best_time_to_visit: BestTimeToVisit,
    val attractions_and_activities: List<Attraction>,
    val personalized_suggestions: PersonalizedSuggestions,
    val special_tips: SpecialTips
) {
    companion object {
        fun default(): TravelGuide {
            return TravelGuide(
                destination = "Hanoi, Vietnam",
                best_time_to_visit = BestTimeToVisit(
                    optimal = "September to November",
                    alternative = "March to April",
                    least_favorable = "May to August"
                ),
                attractions_and_activities = listOf(
                    Attraction(
                        name = "Hoan Kiem Lake",
                        description = "A scenic lake in the heart of Hanoi, known for its peaceful atmosphere and the iconic Turtle Tower.",
                        duration = "1-2 hours",
                        tips = "Visit early in the morning to see locals practicing Tai Chi."
                    ),
                    Attraction(
                        name = "Old Quarter",
                        description = "A bustling area with narrow streets, traditional shops, and vibrant street food.",
                        duration = "Half-day",
                        tips = "Try local street food and explore the unique architecture."
                    ),
                    Attraction(
                        name = "Ho Chi Minh Mausoleum",
                        description = "The final resting place of Vietnam's revolutionary leader, Ho Chi Minh.",
                        duration = "1-2 hours",
                        tips = "Dress respectfully and check the opening hours before visiting."
                    ),
                    Attraction(
                        name = "Temple of Literature",
                        description = "Vietnam's first national university, dedicated to Confucius and scholars.",
                        duration = "1-2 hours",
                        tips = "Visit during weekdays to avoid crowds."
                    ),
                    Attraction(
                        name = "Hanoi Opera House",
                        description = "A beautiful French colonial building hosting various cultural performances.",
                        duration = "1-2 hours",
                        tips = "Check the schedule for performances and book tickets in advance."
                    ),
                    Attraction(
                        name = "Vietnam Museum of Ethnology",
                        description = "A museum showcasing the diverse cultures of Vietnam's 54 ethnic groups.",
                        duration = "2-3 hours",
                        tips = "Allocate enough time to explore both indoor and outdoor exhibits."
                    )
                ),
                personalized_suggestions = PersonalizedSuggestions(
                    history_buffs = "Visit the Vietnam Military History Museum to learn about the country's military past.",
                    foodies = "Try local specialties like Pho (noodle soup) and Bun Cha (grilled pork with noodles) at local eateries.",
                    adventure_seekers = "Take a day trip to Ninh Binh for stunning landscapes and outdoor activities."
                ),
                special_tips = SpecialTips(
                    booking_in_advance = "For popular attractions like the Ho Chi Minh Mausoleum, it's advisable to check visiting hours and plan accordingly.",
                    local_transportation = "Consider using Grab for convenient transportation, or rent a bicycle to explore the city."
                )
            )
        }
    }
}

@Serializable
data class BestTimeToVisit(
    val optimal: String,
    val alternative: String,
    val least_favorable: String
)

@Serializable
data class Attraction(
    val name: String,
    val description: String,
    val duration: String,
    val tips: String
)

@Serializable
data class PersonalizedSuggestions(
    val history_buffs: String,
    val foodies: String,
    val adventure_seekers: String
)

@Serializable
data class SpecialTips(
    val booking_in_advance: String,
    val local_transportation: String
)