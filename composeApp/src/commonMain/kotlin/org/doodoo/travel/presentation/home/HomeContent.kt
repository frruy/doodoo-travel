import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.doodoo.travel.presentation.home.HomeComponent
import org.doodoo.travel.presentation.home.HomeData
import org.doodoo.travel.presentation.home.HomeLabel
import org.doodoo.travel.presentation.home.HomeState

@Composable
fun HomeContent(component: HomeComponent) {
    val state by component.state.subscribeAsState()

    LaunchedEffect(component) {
        component.labels.collect { label ->
            when (label) {
                is HomeLabel.ErrorOccurred -> {
                    // In a real app, you'd want to show this in a Snackbar or Dialog
                    println("Error occurred: ${label.error}")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home Screen",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (val currentState = state) {
            is HomeState.Content -> ContentView(
                homeData = currentState.homeData,
                isLoading = currentState.isLoading,
                onRefresh = component::refresh,
                onUpdateData = component::updateHomeData
            )
            is HomeState.Error -> ErrorView(
                error = currentState.error,
                isLoading = currentState.isLoading,
                onRetry = component::refresh
            )
        }
    }
}

@Composable
private fun ContentView(
    homeData: HomeData,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onUpdateData: (String) -> Unit
) {
    var inputText by remember { mutableStateOf(homeData.someData) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Current Data: ${homeData.someData}",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onRefresh,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Refresh")
        }

        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("New Data") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { onUpdateData(inputText) },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Update Data")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorView(
    error: String,
    isLoading: Boolean,
    onRetry: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onRetry,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Retry")
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}