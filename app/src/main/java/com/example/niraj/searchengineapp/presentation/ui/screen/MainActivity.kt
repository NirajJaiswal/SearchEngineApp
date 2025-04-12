package com.example.niraj.searchengineapp.presentation.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.niraj.searchengineapp.presentation.ui.screen.compose.SearchScreen
import com.example.niraj.searchengineapp.presentation.util.ConnectivityObserver
import com.example.niraj.searchengineapp.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


/**
 *
 * @author Niraj Kumar
 *
 * The main activity of the application.  This activity hosts the search screen
 * and handles observing network connectivity changes.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    private lateinit var searchViewModel: SearchViewModel

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            searchViewModel = hiltViewModel<SearchViewModel>()
            observeConnectivity()
            MaterialTheme {
                SearchScreen(searchViewModel = searchViewModel)
            }
        }
    }

    /**
     * Observes network connectivity changes and updates the [searchViewModel]'s connection status.
     *
     * This function subscribes to the [connectivityObserver]'s connectivity events and updates the
     * [searchViewModel]'s connection status accordingly.  When the connection status changes, it calls
     * `searchViewModel.setConnectionStatus()` with a boolean indicating whether a connection is
     * available (`true`) or not (`false`).  The subscription is added to the [disposables] collection
     * for proper cleanup when the observer is no longer needed.
     */
    private fun observeConnectivity() {
        val disposable = connectivityObserver.observe()
            .subscribe { status ->
                if (::searchViewModel.isInitialized) {
                    searchViewModel.setConnectionStatus(status == ConnectivityObserver.Status.Available)
                }
            }
        disposables.add(disposable)
    }

    /**
     * Called before activity destruction.  Releases resources (e.g., disposables) to prevent leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
