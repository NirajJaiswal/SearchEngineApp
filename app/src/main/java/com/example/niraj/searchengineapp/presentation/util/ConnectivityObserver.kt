package com.example.niraj.searchengineapp.presentation.util

import io.reactivex.rxjava3.core.Flowable

/**
 * Observes network connectivity changes, emitting [Status] updates via a [Flowable].
 */
interface ConnectivityObserver {

    fun observe(): Flowable<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}