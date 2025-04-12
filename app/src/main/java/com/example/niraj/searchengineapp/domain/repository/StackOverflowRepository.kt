package com.example.niraj.searchengineapp.domain.repository

import com.example.niraj.searchengineapp.data.model.StackOverflowResponse
import com.example.niraj.searchengineapp.data.service.StackOverflowApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Repository for interacting with the Stack Overflow API.
 *
 * This class handles the data fetching from the Stack Overflow API
 * and exposes methods for querying questions.  It uses RxJava's `Single`
 * to represent asynchronous operations.  Dependency injection is used
 * to provide the [StackOverflowApi] instance.
 *
 * @property api The [StackOverflowApi] instance used for making API requests.
 *               Injected via constructor injection.
 */
class StackOverflowRepository @Inject constructor(
    private val api: StackOverflowApi
) {
    fun searchQuestions(query: String): Single<StackOverflowResponse> {
        return api.searchQuestions(query)
    }
}
