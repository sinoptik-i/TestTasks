package com.sinoptik_.rxpractice.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sinoptik_.rxpractice.R
import com.sinoptik_.rxpractice.presentation.main_fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.Companion.newInstance())
                .commitNow()
        }
    }
}