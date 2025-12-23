package com.sinoptik_.effectivemobile.practice_kotlin.task2

/*Написать свой делегат, который будет кешировать время запуска приложения.
Раз в 3 секунды выводить закешированное значение в логи Не в UI потоке.*/

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.reflect.KProperty


class StartTimeCacheDelegate {
    private var cachedValue: LocalDateTime? = null
    private var isInitialized = false

    operator fun getValue(thisRef: Any?, property: KProperty<*>): LocalDateTime {
        if (!isInitialized) {
            cachedValue = LocalDateTime.now()
            isInitialized = true
            println("Start time cached: $cachedValue")
        }
        return cachedValue ?: LocalDateTime.now()
    }
}


class StartTimeLogger {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private val startTime: LocalDateTime by StartTimeCacheDelegate()
     fun startLog(scope: CoroutineScope) {
        scope.launch {
            while (true) {
                Log.i(
                    "LOG_TIME",
                    "${Thread.currentThread().name} Cached start time: ${startTime.format(formatter)}"
                )
                delay(3000)
            }
        }
    }
}
