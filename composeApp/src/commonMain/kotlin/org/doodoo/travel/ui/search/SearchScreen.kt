package org.doodoo.travel.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.doodoo.travel.core.Config.GOOGLE_PLACES_API_KEY
import org.doodoo.travel.data.place.model.PlaceDetail

@Composable
fun SearchScreen(component: SearchComponent) {
    val state by component.state.collectAsState()
    var placeId by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Input
        OutlinedTextField(
            value = placeId,
            onValueChange = { placeId = it },
            label = { Text("Enter Place ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Search Button
        Button(
            onClick = { component.searchPlace(placeId) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Search")
        }

        // Loading indicator
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        }

        // Error message
        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Place Details
        state.placeDetail?.let { place ->
            PlaceDetailCard(place)
        }
    }
}

@Composable
private fun PlaceDetailCard(place: PlaceDetail) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Display the cover image if available
            place.photos?.firstOrNull()?.let { photo ->
                val imageUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${photo.photo_reference}&key=$GOOGLE_PLACES_API_KEY"
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = place.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Adjust height as needed
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                text = place.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = place.formatted_address,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            place.rating?.let { rating ->
                Text(
                    text = "Rating: $rating",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            place.formatted_phone_number?.let { phone ->
                Text(
                    text = "Phone: $phone",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            place.website?.let { website ->
                Text(
                    text = "Website: $website",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}