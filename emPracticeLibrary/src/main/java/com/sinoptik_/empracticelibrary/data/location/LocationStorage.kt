package com.sinoptik_.empracticelibrary.data.location

import android.content.Context
import com.sinoptik_.empracticelibrary.data.location.model.LocationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LocationStorage {
    private const val PREFS_NAME = "location_prefs"
    private const val KEY_LAT = "last_lat"
    private const val KEY_LON = "last_lon"
    private const val KEY_TIME = "last_time"

    private val _location = MutableStateFlow<LocationState?>(null)
    val location: StateFlow<LocationState?> = _location.asStateFlow()

    fun init(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (prefs.contains(KEY_LAT)) {
            val savedState = LocationState(
                latitude = prefs.getFloat(KEY_LAT, 0f).toDouble(),
                longitude = prefs.getFloat(KEY_LON, 0f).toDouble(),
                timestamp = prefs.getLong(KEY_TIME, 0L)
            )
            _location.value = savedState
        }
    }

    fun update(context: Context, newState: LocationState) {

        _location.value = newState


        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
            putFloat(KEY_LAT, newState.latitude.toFloat())
            putFloat(KEY_LON, newState.longitude.toFloat())
            putLong(KEY_TIME, newState.timestamp)
            apply()
        }
    }
}
