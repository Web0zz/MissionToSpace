package com.web0zz.cache.di

import android.content.Context
import com.web0zz.cache.AppDatabase
import com.web0zz.cache.dao.LaunchesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.getInstance(appContext)

    @Provides
    @Singleton
    fun provideLaunchesDao(database: AppDatabase): LaunchesDao = database.launchesDao()
}