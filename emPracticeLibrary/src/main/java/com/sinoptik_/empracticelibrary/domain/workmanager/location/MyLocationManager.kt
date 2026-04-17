package com.sinoptik_.empracticelibrary.domain.workmanager.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.sinoptik_.empracticelibrary.data.location.model.LocationState
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MyLocationManager(private val context: Context) {

    private val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val hasAnyLocationPermission: Boolean
        get() = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    // вместо него теперь префы
    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLastLocation(): LocationState? {
        if (!hasAnyLocationPermission) return null

        val location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        Log.d("MyWorker", "MyLocationManager getLastLocation")
        return location?.let {
            LocationState(it.latitude, it.longitude, it.time)
        }
    }

    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun getCurrentLocation(onLocationReceived: (LocationState) -> Unit) {
        Log.d("MyWorker", "getCurrentLocation: $hasAnyLocationPermission")
        if (!hasAnyLocationPermission) return
        requestFreshLocation(onLocationReceived)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun awaitFreshLocation(): LocationState? = suspendCancellableCoroutine { continuation ->
        getCurrentLocation { state ->
            if (continuation.isActive) {
                continuation.resume(state)
            }
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun awaitFreshLocation2(): LocationState? =
        suspendCancellableCoroutine { continuation ->
            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    if (continuation.isActive) {
                        continuation.resume(
                            LocationState(
                                location.latitude,
                                location.longitude,
                                location.time
                            )
                        )
                    }
                    manager.removeUpdates(this) // Важно: отключаем GPS после успеха
                }
                // ... остальные пустые методы
            }

            // Запускаем поиск
            try {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, listener)
            } catch (e: Exception) {
                if (continuation.isActive) continuation.resume(null)
            }

            // Если сработал timeout, принудительно отключаем поиск
            continuation.invokeOnCancellation {
                manager.removeUpdates(listener)
            }
        }


    @RequiresPermission(
        anyOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    fun requestFreshLocation(onLocationReceived: (LocationState) -> Unit) {
        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                val state = LocationState(location.latitude, location.longitude, location.time)
                onLocationReceived(state)
                manager.removeUpdates(this)
            }

            override fun onProviderDisabled(provider: String) {
                Log.d("MyWorker", "requestFreshLocation LocationWorker onProviderDisabled")
            }

            override fun onProviderEnabled(provider: String) {
                Log.d("MyWorker", "requestFreshLocation LocationWorker onProviderEnabled")
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                Log.d("MyWorker", "requestFreshLocation LocationWorker onStatusChanged")
            }
        }
        val provider = if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("MyWorker", "requestFreshLocation LocationWorker GPS_PROVIDER")
            LocationManager.GPS_PROVIDER
        } else {
            Log.d("MyWorker", "requestFreshLocation LocationWorker NETWORK_PROVIDER")
            LocationManager.NETWORK_PROVIDER
        }
        Log.d("MyWorker", "requestFreshLocation $provider")

        manager.requestLocationUpdates(
            provider,
            0L,
            0f,
            listener,
            Looper.getMainLooper()
        )

    /*    manager.requestLocationUpdates(
            provider,
            0L,
            0f,
            listener,
        )*/
    }


}
