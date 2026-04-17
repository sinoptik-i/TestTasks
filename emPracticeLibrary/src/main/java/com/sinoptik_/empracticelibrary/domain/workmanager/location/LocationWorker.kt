package com.sinoptik_.empracticelibrary.domain.workmanager.location

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sinoptik_.empracticelibrary.data.location.LocationStorage
import kotlinx.coroutines.withTimeoutOrNull

class LocationWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    override suspend fun doWork(): Result {
        Log.d("MyWorker", "start fun LocationWorker Work started")
        val myLocationManager = MyLocationManager(applicationContext)
        try{
            myLocationManager.getCurrentLocation { currentLocation ->
                LocationStorage.update(context, currentLocation)
            }
        }
        catch (e:Exception){
            Log.d("MyWorker", "e: ${e.message}",e)
        }
        Log.d("MyWorker", "end fun LocationWorker Work started")
        return Result.success()
    }
}
/*    val oldLocation=myLocationManager.getLastLocation()
        Log.d("MyWorker", "old Location received: ${oldLocation?.latitude}")*/
/*
        val currentLocation = withTimeoutOrNull(15000) {
            myLocationManager.awaitFreshLocation()
        }
*/

/* currentLocation?.let {
     LocationStorage.update(context, it)
 }*/


/*    if (currentLocation != null) {
    LocationStorage.update(context, currentLocation)
    Log.d("MyWorker", "Location received: ${currentLocation.latitude}")
} else {
    Log.d("MyWorker", "Location timeout - no GPS signal")
}*/