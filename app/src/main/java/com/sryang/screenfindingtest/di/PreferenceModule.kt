package com.sarang.torang.di

import com.example.torang_core.data.LocationPreferences
import com.example.torang_core.repository.*
import com.example.torangrepository.*
import com.sryang.screenfindingtest.TestFilterRepository
import com.sryang.screenfindingtest.TestLocationPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule() {
    @Binds
    abstract fun provideLocationPreferences(locationPreferences: LocationPreferencesImpl): LocationPreferences
//    abstract fun provideLocationPreferences(locationPreferences: TestLocationPreferencesImpl): LocationPreferences
}