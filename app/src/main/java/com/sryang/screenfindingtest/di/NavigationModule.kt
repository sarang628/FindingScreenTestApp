package com.sryang.screenfindingtest.di

import android.content.Context
import com.example.torang_core.navigation.RestaurantDetailNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun provide(dummyRestaurantDetailNavigation: DummyRestaurantDetailNavigation): RestaurantDetailNavigation
}


class DummyRestaurantDetailNavigation @Inject constructor() : RestaurantDetailNavigation {
    override fun go(context: Context, restaurantId: Int) {

    }
}

