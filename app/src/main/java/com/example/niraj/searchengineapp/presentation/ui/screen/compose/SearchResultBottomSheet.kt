package com.example.niraj.searchengineapp.presentation.ui.screen.compose

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.niraj.searchengineapp.presentation.util.Constant
import com.example.niraj.searchengineapp.presentation.viewmodel.SearchViewModel

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Composable function that displays a bottom sheet containing a WebView to display search results.
 *
 * @param linkAndTitle A [Pair] containing the URL (link) of the search result and its title.
 * @param searchViewModel The [SearchViewModel] responsible for managing the search state and data.
 * @param onDismiss Callback function to be invoked when the bottom sheet is dismissed.
 *
 */
@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultBottomSheet(
    linkAndTitle: Pair<String, String>,
    searchViewModel: SearchViewModel,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val webView = remember { WebView(context) }
    var isLoading by remember { mutableStateOf(true) }
    val connectionStatus by searchViewModel.connectionStatus.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        sheetState.expand()
    }

    LaunchedEffect(connectionStatus) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                isLoading = false
            }
        }
        webView.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            loadUrl(linkAndTitle.first)
        }
    }

    SearchResultBottomSheetComposable(onDismiss, connectionStatus, linkAndTitle, isLoading, webView)
}

/**
 *
 * Composable function to display a bottom sheet that shows the search result in a WebView.
 *
 * @param onDismiss Callback function to be invoked when the bottom sheet is dismissed.
 * @param connectionStatus Boolean indicating the current network connection status.  `true` if connected, `false` otherwise.
 * @param linkAndTitle Pair containing the URL (link) and title of the search result. The first element is the URL and the second element is the title.
 * @param isLoading Boolean indicating whether the WebView content is currently loading.  `true` if loading, `false` otherwise.
 * @param webView  The WebView instance to display the search result.
 *
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchResultBottomSheetComposable(
    onDismiss: () -> Unit,
    connectionStatus: Boolean,
    linkAndTitle: Pair<String, String>,
    isLoading: Boolean,
    webView: WebView
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.75f)
        ) {
            TopSectionComposable(onDismiss, connectionStatus)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = linkAndTitle.second, color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                if (isLoading) {
                    LoadingIndicator(modifier = Modifier.padding(top = 30.dp))
                } else {
                    AndroidView(modifier = Modifier
                        .fillMaxSize(), factory = { webView }, update = {
                        it.loadUrl(linkAndTitle.first)
                    })
                }
            }
        }
    }
}

/**
 *
 * Composable function for the top section of a screen,typically including a back button and a connection status banner.
 *
 * @param onDismiss Callback function to be executed when the back button is clicked.  Usually navigates the user back.
 * @param connectionStatus Boolean indicating the current connection status (true for connected, false otherwise). This will be reflected in the `ConnectionStatusBanner`.
 */
@Composable
private fun TopSectionComposable(onDismiss: () -> Unit, connectionStatus: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = Constant.EMPTY, modifier = Modifier
                .clickable {
                    onDismiss()
                }
                .weight(0.15f),
            tint = Color.Blue
        )

        ConnectionStatusBanner(
            isConnected = connectionStatus,
            modifier = Modifier
                .weight(0.85f)
                .padding(end = 10.dp)
        )
    }
}
