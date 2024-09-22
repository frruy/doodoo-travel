package org.doodoo.travel

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.doodoo.travel.presentation.root.RootComponent
import org.doodoo.travel.presentation.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(
            component = rootComponent,
        )
    }
}