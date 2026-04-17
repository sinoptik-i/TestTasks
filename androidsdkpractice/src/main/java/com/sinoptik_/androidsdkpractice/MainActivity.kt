package com.sinoptik_.androidsdkpractice

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import com.sinoptik_.empracticelibrary.presentation.navigation.FragmentNavigator
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.androidsdkpractice.ui.navigation.Router
import com.sinoptik_.empracticelibrary.domain.broadcast_receiver.ChargingReceiver
import com.sinoptik_.empracticelibrary.domain.workmanager.location.MyLocationManager

class MainActivity : AppCompatActivity() {

    lateinit var router: Router
    private val chargingReceiver = ChargingReceiver()

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val myLocationManager = MyLocationManager(applicationContext)
        myLocationManager.getCurrentLocation { currentLocation ->
            Log.d("MyWorker", "MA ${currentLocation.longitude} ${currentLocation.latitude}")
        }

        /*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }*/

        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargingReceiver, filter)

        setupNavigation(savedInstanceState)
        /*val navigator = FragmentNavigator(this, R.id.container)
        router = Router(navigator)

        if (savedInstanceState == null) {
            router.navigate(Route.Fragment1)
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!router.goBack()) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            })*/
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        val navigator = FragmentNavigator(this, R.id.container)
        router = Router(navigator)

        if (savedInstanceState == null) {
            router.navigate(Route.Fragment1)
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (!router.goBack()) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(chargingReceiver)
    }
}