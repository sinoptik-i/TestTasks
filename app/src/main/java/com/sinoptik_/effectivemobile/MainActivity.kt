package com.sinoptik_.effectivemobile

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sinoptik_.effectivemobile.practice1.ru66.Task5
import com.sinoptik_.effectivemobile.practice1.task2.StartTimeLogger
import com.sinoptik_.effectivemobile.practice1.task3.Task3
import com.sinoptik_.effectivemobile.ui.theme.EffectiveMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel


class MainActivity : ComponentActivity() {

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        val startTimeLogger = StartTimeLogger()
        startTimeLogger.startLog(scope)
        val task3 = Task3()
        val task5 = Task5(this)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveMobileTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            Log.i("LOG_TASK3", "${task3.findInt()}")
                        }) {
                        Text("Task3")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                           task5.save(scope)
                        }) {
                        Text("Task5 save")
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            task5.load()
                        }) {
                        Text("Task5 load")
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}

