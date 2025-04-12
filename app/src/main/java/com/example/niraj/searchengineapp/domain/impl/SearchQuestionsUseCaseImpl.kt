package com.example.niraj.searchengineapp.domain.impl

import com.example.niraj.searchengineapp.data.model.StackOverflowResponse
import com.example.niraj.searchengineapp.domain.repository.StackOverflowRepository
import com.example.niraj.searchengineapp.domain.usecase.SearchQuestionsUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Implementation of the [SearchQuestionsUseCase] interface.
 *
 * @property repository The repository responsible for handling data operations related to
 *                     Stack Overflow, specifically question searching in this case.
 */
class SearchQuestionsUseCaseImpl @Inject constructor(
    private val repository: StackOverflowRepository
) : SearchQuestionsUseCase {
    override operator fun invoke(query: String): Single<StackOverflowResponse> {
        return repository.searchQuestions(query)
    }
}