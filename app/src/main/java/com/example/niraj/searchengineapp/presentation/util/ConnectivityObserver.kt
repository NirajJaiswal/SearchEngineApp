package com.example.niraj.searchengineapp.presentation.util

import io.reactivex.rxjava3.core.Flowable

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Observes network connectivity changes, emitting [Status] updates via a [Flowable].
 */
interface ConnectivityObserver {

    fun observe(): Flowable<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}