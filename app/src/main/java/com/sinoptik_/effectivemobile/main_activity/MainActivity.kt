package com.sinoptik_.effectivemobile.main_activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.sinoptik_.effectivemobile.di.App
import com.sinoptik_.effectivemobile.practice_kotlin.ru66.Task5
import com.sinoptik_.effectivemobile.practice_kotlin.task2.StartTimeLogger
import com.sinoptik_.effectivemobile.practice_kotlin.task3.Task3
import com.sinoptik_.effectivemobile.ui.theme.EffectiveMobileTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

class MainActivity : ComponentActivity() {

    val scope = CoroutineScope(Dispatchers.IO)

    private val viewModel: ViewModelMA by viewModels {
        (application as App).appComponent.getViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel.startNetworkMonitor(
            { message ->
                showNetworkToast(message)
            }
        )
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
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.testFlowersDb()
                        }) {
                        Text("Room Task1")
                    }
                }
            }
        }
    }

    private fun showNetworkToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    private fun timeLogger() {
        val startTimeLogger = StartTimeLogger()
        startTimeLogger.startLog(scope)
    }

    override fun onStop() {
        viewModel.stopNetworkMonitor()
        super.onStop()
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}