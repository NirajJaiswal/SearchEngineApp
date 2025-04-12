package com.example.niraj.searchengineapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.niraj.searchengineapp.data.model.Question
import com.example.niraj.searchengineapp.domain.usecase.SearchQuestionsUseCase
import com.example.niraj.searchengineapp.presentation.util.Constant
import com.example.niraj.searchengineapp.presentation.util.Constant.EMPTY
import com.example.niraj.searchengineapp.presentation.util.Constant.ONE
import com.example.niraj.searchengineapp.presentation.util.Constant.ONE_MORE_CHAR
import com.example.niraj.searchengineapp.presentation.util.Constant.THREE
import com.example.niraj.searchengineapp.presentation.util.Constant.THREE_MORE_CHAR
import com.example.niraj.searchengineapp.presentation.util.Constant.TWO
import com.example.niraj.searchengineapp.presentation.util.Constant.TWO_MORE_CHAR
import com.example.niraj.searchengineapp.presentation.util.Constant.ZERO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * @author Niraj Kumar
 *
 * This is the view model class for the main screen.
 *
 * @property searchQuestionsUseCase The use case for searching questions.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQuestionsUseCase: SearchQuestionsUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Question>>(emptyList())
    val searchResults: StateFlow<List<Question>> = _searchResults

    private val _errorMessage = MutableStateFlow(EMPTY)
    val errorMessage: StateFlow<String> = _errorMessage

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _connectionStatus = MutableStateFlow(true)
    val connectionStatus: StateFlow<Boolean> = _connectionStatus

    private val compositeDisposable = CompositeDisposable()

    /**
     * Searches for questions based on the provided query string.
     *
     * The search is performed only if the query string has at least 3 characters.
     * Otherwise, an error message is displayed to the user, and the search results are cleared.
     *
     * @param query The search query string.  Must be at least 3 characters long to initiate a search.
     */
    fun searchQuestions(query: String) {
        if (query.length < THREE) {
            val message = when (query.length) {
                TWO -> ONE_MORE_CHAR
                ONE -> TWO_MORE_CHAR
                ZERO -> THREE_MORE_CHAR
                else -> EMPTY
            }
            _errorMessage.value = message
            _searchResults.value = emptyList()
            return
        }

        _loading.value = true
        _errorMessage.value = EMPTY

        val disposable = searchQuestionsUseCase(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    _searchResults.value = response.items
                    _loading.value = false
                },
                { error ->
                    _errorMessage.value = "${Constant.ERROR} ${error.message}"
                    _loading.value = false
                    _searchResults.value = emptyList()
                }
            )
        compositeDisposable.add(disposable)
    }

    /**
     * Called when the ViewModel is no longer used and will be destroyed.
     */
    public override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * Sets the connection status of the application.
     *
     * @param isConnected A boolean value representing the connection status.  `true` if connected, `false` otherwise.
     */
    fun setConnectionStatus(isConnected: Boolean) {
        _connectionStatus.value = isConnected
    }
}