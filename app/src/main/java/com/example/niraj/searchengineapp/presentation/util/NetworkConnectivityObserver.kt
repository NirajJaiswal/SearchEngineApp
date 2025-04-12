package com.example.niraj.searchengineapp.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * An implementation of [ConnectivityObserver] that uses the Android [ConnectivityManager]
 * to observe network connectivity changes. It provides updates on network availability,
 * loss, and unavailability via a [Flowable] stream.
 *
 * @param context The Android application context.  Used to access the [ConnectivityManager] system service.
 *
 */
class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flowable<ConnectivityObserver.Status> {
        return Flowable.create({ emitter ->
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) =
                    emitter.onNext(ConnectivityObserver.Status.Available)

                override fun onLosing(network: Network, maxMsToLive: Int) =
                    emitter.onNext(ConnectivityObserver.Status.Losing)

                override fun onLost(network: Network) =
                    emitter.onNext(ConnectivityObserver.Status.Lost)

                override fun onUnavailable() =
                    emitter.onNext(ConnectivityObserver.Status.Unavailable)
            }

            val initialStatus = connectivityManager.activeNetwork?.let {
                ConnectivityObserver.Status.Available
            } ?: ConnectivityObserver.Status.Unavailable

            emitter.onNext(initialStatus)

            connectivityManager.registerDefaultNetworkCallback(callback)

            emitter.setCancellable {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }, BackpressureStrategy.LATEST)
            .distinctUntilChanged()
    }
}