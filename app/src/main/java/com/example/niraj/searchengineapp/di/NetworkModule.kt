package com.example.niraj.searchengineapp.di


import android.content.Context
import com.example.niraj.searchengineapp.data.service.StackOverflowApi
import com.example.niraj.searchengineapp.domain.impl.SearchQuestionsUseCaseImpl
import com.example.niraj.searchengineapp.domain.usecase.SearchQuestionsUseCase
import com.example.niraj.searchengineapp.presentation.util.ConnectivityObserver
import com.example.niraj.searchengineapp.presentation.util.Constant
import com.example.niraj.searchengineapp.presentation.util.NetworkConnectivityObserver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *
 * @created 12/04/2025
 * @author Niraj Kumar
 *
 * Dagger Hilt Module for providing network-related dependencies.
 *
 * This module provides dependencies related to network communication, including
 * OkHttpClient, Retrofit, and the StackOverflowApi interface. It also provides
 * a [ConnectivityObserver] for monitoring network connectivity changes.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindSearchQuestionsUseCase(
        searchQuestionsUseCaseImpl: SearchQuestionsUseCaseImpl
    ): SearchQuestionsUseCase

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideStackOverflowApi(retrofit: Retrofit): StackOverflowApi {
            return retrofit.create(StackOverflowApi::class.java)
        }

        @Provides
        @Singleton
        fun provideConnectivityObserver(@ApplicationContext applicationContext: Context): ConnectivityObserver =
            NetworkConnectivityObserver(applicationContext)

    }
}
