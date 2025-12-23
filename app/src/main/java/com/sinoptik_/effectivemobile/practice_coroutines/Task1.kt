package com.sinoptik_.effectivemobile.practice_coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


//Напишите аналоги операторов throttleFisrt и throttleLatest из RxJava для Flow.
class Task1 {

    fun <T : Any> Flow<T>.throttleFirst(duration: Long): Flow<T> {
        var endTime = 0L
        return this
            .filter {
                if (System.currentTimeMillis() >= endTime) {
                    endTime = System.currentTimeMillis() + duration
                    true
                } else {
                    false
                }
            }
    }

    fun <T : Any> Flow<T>.throttleLatest(duration: Long): Flow<T> = flow {
        var endTime = 0L
        var latest: T? = null
        var newVal = false
        collect { it ->
            if (endTime == 0L && !newVal) {
                emit(it)
                newVal = true
            }
            if (System.currentTimeMillis() >= endTime) {
                endTime = System.currentTimeMillis() + duration
                if (latest != null && newVal) {
                    emit(latest!!)
                    newVal = false
                }
            }
            latest = it
            newVal = true
        }
    }
}