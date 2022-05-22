package com.sryang.screenfindingtest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import com.example.torang_core.data.model.*
import com.example.torang_core.repository.FindRepository
import com.example.torang_core.repository.RequestLocationResult
import com.example.torang_core.util.Logger
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

    //최초 위치요쳥을 false로 설정 시 화면단에서 요청해야함
    private val isFirstRequestLocation = MutableStateFlow(false)
    private val isRequestingLocation = MutableStateFlow(false)
    private val searchBoundRestaurantTrigger = MutableStateFlow(false)
    private val currentPosition = MutableStateFlow(0)

    // 위치요청을 처음 했는지 여부
    private val isFirstRequestLocationPermission = MutableStateFlow(false)

    // 권한이 있는지 여부
    private val hasGrantPermission: MutableStateFlow<Int> =
        MutableStateFlow<Int>(context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION))

    private val searchedRestaurants = MutableStateFlow<List<Restaurant>>(ArrayList())

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

    /**
     * 화면 첫 진입 시 위치를 요청해야하는지에 대한 상태
     */
    override fun getIsFirstRequestLocation(): StateFlow<Boolean> {
        return isFirstRequestLocation
    }

    /**
     * 위치를 요청했다고 알려줌
     */
    override suspend fun notifyRequestLocation() : RequestLocationResult {
        //성공
        //return RequestLocationResult.SUCCESS
        //설정 화면 이동 팝업
        //return RequestLocationResult.PERMISSION_DENIED
        //isRequestingLocation.emit(false)
        //isFirstRequestLocation.emit(true)
        //isRequestingLocation.emit(true)
        //위치 권한 필요 팝업
        return RequestLocationResult.NEED_LOCATION_PERMISSION
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

    override suspend fun isFirstRequestLocationPermission(): StateFlow<Boolean> {
        return isFirstRequestLocationPermission
    }

    //위치원한 요청에 대한 사용자 응답
    override suspend fun requestLocationPermission(b: Boolean) {
        //팝업 계속 나오게 하기위해 처음 요청인지 테스트로 초기화
        isFirstRequestLocationPermission.emit(false)
    }


    override fun hasGrantPermission(): MutableStateFlow<Int> {
        return hasGrantPermission
    }

    override suspend fun permissionGranated() {
        hasGrantPermission.emit(PackageManager.PERMISSION_GRANTED)
    }
}