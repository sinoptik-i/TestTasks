package com.sinoptik_.koinexample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sinoptik_.koinexample.ui.theme.EffectiveMobileTheme
import com.sinoptik_.koinexample.utils.ActivityTracker
import com.sinoptik_.koinexample.utils.TestMessagePrinter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope



const val MA_EXTRA_DATA="MA_EXTRA_DATA"
class MainActivity : ComponentActivity(), AndroidScopeComponent {


    override val scope: Scope by activityScope()

    private val printer: TestMessagePrinter by inject()

    private val tracker: ActivityTracker by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(this.javaClass.simpleName, printer.printHello())

        tracker.cryMyName()
        startMyService()

        setContent {
            EffectiveMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startMyService() {
        val intent = Intent(this, MyService::class.java)
            .apply {
                putExtra(MA_EXTRA_DATA, "DATA_FROM_MA")
            }
        startService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MyService::class.java))
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
