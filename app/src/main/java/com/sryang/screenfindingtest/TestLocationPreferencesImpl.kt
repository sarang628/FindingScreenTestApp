package com.sryang.screenfindingtest

import android.content.Context
import android.content.SharedPreferences
import com.example.torang_core.data.LocationPreferences
import com.example.torang_core.util.Logger
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestLocationPreferencesImpl @Inject constructor(@ApplicationContext val context: Context) :
    LocationPreferences {
    override suspend fun isFirstRequestLocationPermission(): Boolean {
        //val b = getPref().getBoolean("isFirstRequestLocationPermission", true)
        return true
    }

    override fun requestLocationPermission() {
        /*getPref().edit()
            .putBoolean("isFirstRequestLocationPermission", false)
            .commit()*/
    }

    fun getPref(): SharedPreferences {
        return context.getSharedPreferences("torang", Context.MODE_PRIVATE)
    }
}