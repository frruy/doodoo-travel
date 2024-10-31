package org.doodoo.travel.data.travelguide.repository

import io.ktor.client.*
import org.doodoo.travel.data.travelguide.model.TravelGuide
import org.doodoo.travel.ulti.parseTravelGuide
import org.koin.core.component.KoinComponent

class TravelGuideRepositoryImpl : TravelGuideRepository, KoinComponent {
    override suspend fun getData(input: String): TravelGuide {

        val jsonString = """
    {
      "destination": "Da Nang, Vietnam",
      "best_time_to_visit": {
        "optimal": "February to May",
        "alternative": "June to August",
        "least_favorable": "September to January"
      },
      "attractions_and_activities": [
        {
          "name": "My Khe Beach",
          "description": "One of the most beautiful beaches in Vietnam, perfect for swimming, sunbathing, and water sports.",
          "duration": "2-4 hours",
          "tips": "Visit early in the morning or late afternoon to avoid the midday heat."
        },
        {
          "name": "Marble Mountains",
          "description": "A cluster of five marble and limestone hills with caves, tunnels, and temples.",
          "duration": "2-3 hours",
          "tips": "Wear comfortable shoes and bring water. The climb can be steep in some areas."
        },
        {
          "name": "Ba Na Hills and Golden Bridge",
          "description": "A hill station with a French village, amusement park, and the famous Golden Bridge held by giant hands.",
          "duration": "Full day",
          "tips": "Arrive early to avoid crowds and book tickets in advance."
        },
        {
          "name": "Dragon Bridge",
          "description": "A bridge shaped like a dragon that breathes fire and water on weekends and holidays.",
          "duration": "1 hour",
          "tips": "Visit on a weekend night to see the fire and water show at 9 PM."
        },
        {
          "name": "Hoi An Ancient Town",
          "description": "A UNESCO World Heritage site located about 30 km from Da Nang, known for its well-preserved ancient buildings and lantern-lit streets.",
          "duration": "Half-day to full day",
          "tips": "Visit in the late afternoon and stay until evening to enjoy the lanterns."
        },
        {
          "name": "Son Tra Peninsula (Monkey Mountain)",
          "description": "A scenic area with lush forests, the Linh Ung Pagoda, and panoramic views of the city and coastline.",
          "duration": "3-4 hours",
          "tips": "Rent a motorbike or hire a car for a more comfortable visit."
        }
      ],
      "personalized_suggestions": {
        "history_buffs": "Visit the Cham Museum to explore artifacts from the ancient Champa civilization.",
        "foodies": "Try local specialties like Mi Quang (turmeric noodles) and Banh Xeo (Vietnamese pancakes) at local eateries.",
        "adventure_seekers": "Consider a day trip to the Hai Van Pass for breathtaking views and a thrilling motorbike ride."
      },
      "special_tips": {
        "booking_in_advance": "For popular attractions like Ba Na Hills, it's advisable to book tickets in advance, especially during peak seasons.",
        "local_transportation": "Consider renting a motorbike for flexibility, but always wear a helmet and drive safely."
      }
    }
    """
        val travelGuide = parseTravelGuide(jsonString)

        return travelGuide
//        return httpClient.get("https://api.example.com/data").body()
    }
}