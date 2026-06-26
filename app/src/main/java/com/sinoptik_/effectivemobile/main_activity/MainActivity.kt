package com.sinoptik_.effectivemobile.main_activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.sinoptik_.effectivemobile.di.App
import com.sinoptik_.effectivemobile.di.test.ACar1
import com.sinoptik_.effectivemobile.di.test.ACar2
import com.sinoptik_.effectivemobile.di.test.ACar3
import com.sinoptik_.effectivemobile.di.test.Car
import com.sinoptik_.effectivemobile.practice_kotlin.ru66.Task5
import com.sinoptik_.effectivemobile.practice_kotlin.task2.StartTimeLogger
import com.sinoptik_.effectivemobile.practice_kotlin.task3.Task3
import com.sinoptik_.effectivemobile.ui.theme.EffectiveMobileTheme
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    val scope = CoroutineScope(Dispatchers.IO)

//    @Inject
//    @ACar1
//    lateinit var car1: Car
//
//    @Inject
//    @ACar2
//    lateinit var car2: Car
//
//    @Inject
//    @ACar3
//    lateinit var car3: Car




    override fun onCreate(savedInstanceState: Bundle?) {

        val task3 = Task3()
        val task5 = Task5(this)
        /*timeLogger()
        annotationTest()*/
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveMobileTheme {
                Column(
                    modifier = Modifier.Companion.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        onClick = {
                            Log.i("LOG_TASK3", "${task3.findInt()}")
                        }) {
                        Text("Task3")
                    }
                    Button(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        onClick = {
                            task5.save(scope)
                        }) {
                        Text("Task5 save")
                    }
                    Button(
                        modifier = Modifier.Companion.fillMaxWidth(),
                        onClick = {
                            task5.load()
                        }) {
                        Text("Task5 load")
                    }
                }
            }
        }
    }

//    private fun annotationTest() {
//
//        (application as App).carComponent.inject(this)
//        car1.drive()
//        car2.drive()
//        car3.drive()
//    }

    private fun timeLogger() {
        val startTimeLogger = StartTimeLogger()
        startTimeLogger.startLog(scope)
    }


    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}