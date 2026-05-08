package com.sinoptik_.koinexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sinoptik_.koinexample.utils.AnyApi
import com.sinoptik_.koinexample.utils.AnyApi2
import com.sinoptik_.koinexample.utils.ServiceLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.inject

class MyService : Service(), KoinComponent
{

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    val api: AnyApi = GlobalContext.get().get()// эт если б KoinComponent не было.

    private var logger: ServiceLogger? = null

    private var scope: Scope? = null

    override fun onCreate() {
        super.onCreate()

        scope = getKoin().getOrCreateScope(
            scopeId = "scope_id_1",
            qualifier = named<MyService>()
        )


        val anyApi2 = getKoin().get<AnyApi2>()
        val anyApi2v2: AnyApi2 by inject()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val message = intent?.getStringExtra(MA_EXTRA_DATA) ?: "empty"

        logger = scope?.get<ServiceLogger>(){ parametersOf(message) }
        logger?.cryMyName()

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }

}