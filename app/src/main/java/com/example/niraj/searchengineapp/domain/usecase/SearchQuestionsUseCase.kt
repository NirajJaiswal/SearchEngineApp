package com.example.niraj.searchengineapp.domain.usecase

import com.example.niraj.searchengineapp.data.model.StackOverflowResponse
import io.reactivex.rxjava3.core.Single

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Use case for searching questions on Stack Overflow.
 *
 * This interface defines the contract for searching Stack Overflow questions based on a given query string.
 * The `invoke` operator function executes the search and returns a [Single] emitting a [StackOverflowResponse]
 * representing the search results.
 */
interface SearchQuestionsUseCase {
    operator fun invoke(query: String): Single<StackOverflowResponse>
}