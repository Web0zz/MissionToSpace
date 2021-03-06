package com.web0zz.network.di

import android.content.Context
import com.web0zz.network.SpaceXService
import com.web0zz.network.util.Constants.BASE_URL
import com.web0zz.network.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext appContext: Context): NetworkHelper =
        NetworkHelper(appContext)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkhttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideSpaceXService(retrofit: Retrofit): SpaceXService =
        SpaceXService(retrofit)

    private fun provideOkhttp(): OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .build()
}