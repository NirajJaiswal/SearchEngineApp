package com.example.niraj.searchengineapp.data.service

import com.example.niraj.searchengineapp.data.model.StackOverflowResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 *  Interface for interacting with the Stack Overflow API.
 */
interface StackOverflowApi {
    @GET("search/advanced")
    fun searchQuestions(
        @Query("q") query: String,
        @Query("site") site: String = "stackoverflow",
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "creation"
    ): Single<StackOverflowResponse>
}