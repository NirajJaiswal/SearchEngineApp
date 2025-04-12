package com.example.niraj.searchengineapp.presentation.ui.screen.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.niraj.searchengineapp.data.model.Question
import com.example.niraj.searchengineapp.presentation.util.Constant
import com.example.niraj.searchengineapp.presentation.util.Constant.EMPTY
import com.example.niraj.searchengineapp.presentation.util.toTimeDateString
import com.example.niraj.searchengineapp.presentation.viewmodel.SearchViewModel

/**
 *
 * @author Niraj Kumar
 * @created 12/04/2025
 *
 * The main screen of the application, responsible for displaying the search bar,
 * search results, loading indicators, error messages, and connection status.
 *
 * @param searchViewModel The [SearchViewModel] instance used to manage the UI state and interactions.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel) {
    val searchResults by searchViewModel.searchResults.collectAsStateWithLifecycle()
    val errorMessage by searchViewModel.errorMessage.collectAsStateWithLifecycle()
    val loading by searchViewModel.loading.collectAsStateWithLifecycle()
    val connectionStatus by searchViewModel.connectionStatus.collectAsStateWithLifecycle()
    var showBottomSheet by remember { mutableStateOf(false) }
    var linkAndTitle by remember { mutableStateOf(Pair(EMPTY, EMPTY)) }

    if (showBottomSheet) {
        SearchResultBottomSheet(linkAndTitle = linkAndTitle, searchViewModel = searchViewModel) {
            showBottomSheet = false
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                ConnectionStatusBanner(
                    isConnected = connectionStatus,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            modifier = Modifier.padding(end = 10.dp)
        )
        SearchBar(
            onSearch = { query -> searchViewModel.searchQuestions(query) },
            isConnected = connectionStatus
        )

        if (errorMessage.isNotEmpty()) {
            ErrorMessage(message = errorMessage)
        } else if (loading) {
            LoadingIndicator()
        } else {
            QuestionList(questions = searchResults, onItemClick = { title, link ->
                linkAndTitle = Pair(link, title)
                showBottomSheet = !showBottomSheet
            })
        }
    }
}

/**
 * A composable function that displays a search bar with a text field and a search button.
 *
 * @param onSearch A lambda function that is called when the search button is clicked.  It takes the search query as a string argument.
 * @param isConnected A boolean indicating whether the search functionality is enabled.
 *
 */
@Composable
private fun SearchBar(onSearch: (String) -> Unit, isConnected: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var text by remember { mutableStateOf(EMPTY) }
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .weight(0.7f)
                .padding(8.dp),
            placeholder = { Text(Constant.SEARCH_STACK_OVERFLOW) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = Constant.SEARCH) },
            singleLine = true,
            enabled = isConnected,
        )
        Button(
            onClick = { onSearch(text) }, modifier = Modifier.weight(0.3f), enabled = isConnected
        ) {
            Text(text = Constant.SEARCH)
        }
    }
}

/**
 * Displays a list of questions.
 *
 * If the list is empty, it displays a "Search" icon indicating that no questions are currently available.
 * Otherwise, it displays a vertically scrollable list of questions using [LazyColumn].  Each question is
 * represented by a [QuestionItem] composable.
 *
 * @param questions The list of [Question] objects to display.
 * @param onItemClick A callback function that is invoked when a question item in the list is clicked.
 *
 */
@Composable
private fun QuestionList(questions: List<Question>, onItemClick: (String, String) -> Unit) {
    if (questions.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = EMPTY,
                modifier = Modifier.size(300.dp)
            )
        }

    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(questions) { question ->
                QuestionItem(question = question, onItemClick = onItemClick)
            }
        }
    }
}

/**
 *
 * Composable function to display a single question item.
 *
 * @param question The [Question] object containing the question data to be displayed.
 * @param onItemClick A lambda function to be executed when the item is clicked. It takes the question title and link as parameters.
 *                 This function is typically used to navigate to the detailed question screen or open the question in a browser.
 *
 * Clicking on the surface triggers the [onItemClick] lambda.
 *
 */
@Composable
private fun QuestionItem(question: Question, onItemClick: (String, String) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(question.title, question.link) },
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = question.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Author : ${question.owner.displayName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Created : ${question.creationDate.toTimeDateString()}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
