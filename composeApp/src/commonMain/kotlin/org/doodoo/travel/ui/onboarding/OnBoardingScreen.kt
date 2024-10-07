package org.doodoo.travel.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.doodoo.travel.core.database.User

@Composable
fun OnboardingScreen(component: OnboardingComponent) {
    val state by component.state.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OnboardingHeader()
            when (state) {
                is OnboardingState.Content -> UserInfoSection(state as OnboardingState.Content, component::createUser)
                is OnboardingState.Error -> ErrorSection(state as OnboardingState.Error)
//                null -> LoadingSection()
            }
        }
    }

    // Collect labels (side effects)
    LaunchedEffect(component) {
        component.labels.collect { label ->
            when (label) {
                is OnboardingLabel.ErrorOccurred -> {
                    // Show error message, e.g., using a Snackbar
                }
            }
        }
    }
}

@Composable
fun OnboardingHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Welcome to YourApp",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Let's get to know you!",
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground
        )
    }
}

@Composable
fun UserInfoSection(state: OnboardingState.Content, onCreateUser: (User) -> Unit) {
    var name by remember { mutableStateOf(state.user.name) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "What's your name?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = state.error != null
        )
        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onCreateUser(state.user.copy(name = name)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = true,
//            enabled = !state.isLoading && name.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                disabledBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.3f)
            )
        ) {
            Text(
                text = "Continue",
                fontSize = 18.sp,
                color = Color.White
            )
//            if (state.isLoading) {
//                CircularProgressIndicator(color = Color.White)
//            } else {
//                Text(
//                    text = "Continue",
//                    fontSize = 18.sp,
//                    color = Color.White
//                )
//            }
        }
    }
}

@Composable
fun ErrorSection(state: OnboardingState.Error) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "An error occurred",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colors.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = state.error,
            fontSize = 16.sp,
            color = MaterialTheme.colors.error
        )
    }
}

//@Composable
//fun LoadingSection() {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        CircularProgressIndicator()
//    }
//}