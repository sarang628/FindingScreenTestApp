package com.sryang.screenfindingtest

import android.content.Context
import com.example.torang_core.data.model.*
import com.example.torang_core.repository.FindRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestFindRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) :
    FindRepository {

    val searchedRestaurants = MutableStateFlow<List<Restaurant>>(ArrayList())

    override fun getSearchedRestaurant(): Flow<List<Restaurant>> {
        return searchedRestaurants
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
        searchType: SearchType
    ) {
        val list: ArrayList<Restaurant> = ArrayList()
        list.add(Restaurant().apply {
            lat = northEastLatitude
            lon = northEastLongitude
        })

        context.assets.open("restaurants.json").use { inputStream ->
            com.google.gson.stream.JsonReader(inputStream.reader()).use { jsonReader ->
                val imageType = object : TypeToken<List<Restaurant>>() {}.type
                val imageList: List<Restaurant> = Gson().fromJson(jsonReader, imageType)

                searchedRestaurants.emit(imageList)
            }
        }
    }


    //최초 위치요쳥을 false로 설정 시 화면단에서 요청해야함
    private val isFirstRequestLocation = MutableStateFlow(false)
    private val isRequestingLocation = MutableStateFlow(false)
    private val searchBoundRestaurantTrigger = MutableStateFlow(false)
    private val currentPosition = MutableStateFlow(0)

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
        //delay(3000)
        //isRequestingLocation.emit(false)
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

    override suspend fun searchBoundRestaurant() {
        searchBoundRestaurantTrigger.emit(true)
        delay(1)
        searchBoundRestaurantTrigger.emit(false)
    }

    override fun getSearchBoundRestaurnatTrigger(): StateFlow<Boolean> {
        return searchBoundRestaurantTrigger
    }

    override suspend fun setCurrentPosition(position: Int) {
        currentPosition.emit(position)
    }

    override fun getCurrentPosition(): StateFlow<Int> {
        return currentPosition
    }
}