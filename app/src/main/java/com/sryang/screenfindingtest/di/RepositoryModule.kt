package com.sarang.torang.di

import android.content.Context
import com.example.torang_core.data.AppDatabase
import com.example.torang_core.data.model.*
import com.example.torang_core.repository.*
import com.example.torangrepository.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyReviewsRepositoryProvider() {
    @Binds
    abstract fun provideMyReviewsRepository(myReviewsRepositoryImpl: MyReviewsRepositoryImpl): MyReviewsRepository

    @Binds
    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun provideMyReviewRepository(myReviewRepositoryImpl: MyReviewRepositoryImpl): MyReviewRepository

    @Binds
    abstract fun provideNationRepository(nationRepositoryImpl: NationRepositoryImpl): NationRepository

    @Binds
    abstract fun provideFindRepository(findRepository: TestFindRepositoryImpl): FindRepository
}

@Singleton
class TestFindRepositoryImpl @Inject constructor() : FindRepository {
    override fun getSearchedRestaurant(): Flow<List<Restaurant>> {
        return MutableStateFlow(ArrayList())
    }

    override suspend fun searchRestaurant(
        distances: Distances?,
        restaurantType: ArrayList<RestaurantType>?,
        prices: Prices?,
        ratings: ArrayList<Ratings>?,
        latitude: Double,
        longitude: Double,
        northEastLatitude: Double,
        northEastLongitude: Double,
        southWestLatitude: Double,
        southWestLongitude: Double,
        searchType: Filter.SearchType
    ) {

    }


    val isFirstRequestLocation = MutableStateFlow(false)
    val isRequestingLocation = MutableStateFlow(false)

    override fun getIsFirstRequestLocation(): StateFlow<Boolean> {
        return isFirstRequestLocation
    }

    override suspend fun notifyRequestedLocation() {
        isFirstRequestLocation.emit(true)
        isRequestingLocation.emit(true)
    }

    override fun isRequestingLocation(): StateFlow<Boolean> {
        return isRequestingLocation
    }

    override suspend fun onReceiveLocation() {
        isRequestingLocation.emit(false)
    }

}