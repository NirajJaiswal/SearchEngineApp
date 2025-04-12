package com.example.niraj.searchengineapp.presentation.ui.screen.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Displays an error message with the specified text.
 *
 * This composable renders a [Text] element displaying the provided [message] with
 * styling to indicate an error state.  The text color is set to the current
 *
 * @param message The error message to display.
 *
 */
@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

/**
 *
 * A composable that displays a loading indicator in the center of the screen.
 *
 * @param modifier Modifier to be applied to the loading indicator's container.
 *                 Defaults to filling the maximum available size.
 *
 */
@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
