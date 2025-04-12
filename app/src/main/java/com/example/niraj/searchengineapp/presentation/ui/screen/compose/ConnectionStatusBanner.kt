package com.example.niraj.searchengineapp.presentation.ui.screen.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.niraj.searchengineapp.presentation.util.Constant

/**
 * Displays a banner indicating the internet connection status.
 *
 * The banner is only visible when there is no internet connection (`isConnected` is `false`).
 * When a connection loss is detected, the banner appears immediately and disappears after a 1-second delay.
 *
 * @param isConnected A boolean indicating whether the device is currently connected to the internet.
 */
@Composable
fun ConnectionStatusBanner(isConnected: Boolean, modifier: Modifier) {
    Column(
        modifier = modifier
            .height(25.dp)
            .background(
                color = Color.Green.takeIf { isConnected } ?: Color.Red
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {
        Text(
            text = if (isConnected) Constant.CONNECTED else Constant.NO_INTERNET,
            color = Color.White,
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            fontWeight = FontWeight.Bold
        )
    }
}


