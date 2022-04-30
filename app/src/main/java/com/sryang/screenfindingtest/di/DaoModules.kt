package com.sryang.screenfindingtest.di

import android.content.Context
import com.example.torang_core.data.AppDatabase
import com.example.torang_core.data.dao.RestaurantDao
import com.example.torang_core.data.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DaoModules {
    /** 로컬 데이터베이스의 사용자 관리 DAO 제공 */
    @Provides
    fun provideRestaurantDao(appDatabase: AppDatabase): RestaurantDao {
        return appDatabase.restaurantDao()
    }

    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao {
        return appDatabase.searchDao()
    }
}
