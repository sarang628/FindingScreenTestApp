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
import kotlinx.coroutines.delay
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


    //최초 위치요쳥을 false로 설정 시 화면단에서 요청해야함
    val isFirstRequestLocation = MutableStateFlow(false)
    val isRequestingLocation = MutableStateFlow(false)

    /**
     * 화면 첫 진입 시 위치를 요청해야하는지에 대한 상태
     */
    override fun getIsFirstRequestLocation(): StateFlow<Boolean> {
        return isFirstRequestLocation
    }

    /**
     * 위치를 요청했다고 알려줌
     */
    override suspend fun notifyRequestLocation() {
        isFirstRequestLocation.emit(true)
        isRequestingLocation.emit(true)
        //3초있다가 위치를 받은것으로 처리하는 테스트코드
        delay(3000)
        isRequestingLocation.emit(false)
    }

    /**
     * 현재 위치를 요청중인지 상태
     */
    override fun isRequestingLocation(): StateFlow<Boolean> {
        return isRequestingLocation
    }

    /**
     * 위치를 받았다고 알려줌
     */
    override suspend fun notifyReceiveLocation() {
        isRequestingLocation.emit(false)
    }

}