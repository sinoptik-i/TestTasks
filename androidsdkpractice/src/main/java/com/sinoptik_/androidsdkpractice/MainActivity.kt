package com.sinoptik_.androidsdkpractice

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.sinoptik_.androidsdkpractice.ui.navigation.FragmentNavigator
import com.sinoptik_.androidsdkpractice.ui.navigation.Route
import com.sinoptik_.androidsdkpractice.ui.navigation.Router

class MainActivity : AppCompatActivity() {

    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}