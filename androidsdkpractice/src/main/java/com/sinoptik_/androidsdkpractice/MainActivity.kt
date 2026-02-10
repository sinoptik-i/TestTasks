package com.sinoptik_.androidsdkpractice

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.sinoptik_.androidsdkpractice.ui.navigation.FragmentNavigator
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.androidsdkpractice.ui.navigation.Router
import com.sinoptik_.empracticelibrary.domain.broadcast_receiver.ChargingReceiver

class MainActivity : AppCompatActivity() {

    lateinit var router: Router
    private val chargingReceiver = ChargingReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                101
            )
        }

        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargingReceiver, filter)

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