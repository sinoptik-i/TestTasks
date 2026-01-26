package com.sinoptik_.effectivemobile.practice_rx

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class Task2 {

    val num7StreamPeriod1Sec = Observable.intervalRange(
        1,
        7,
        0,
        1,
        TimeUnit.SECONDS
    )


    val letter6StreamPeriod2SecA = Observable.intervalRange(
        0,
        6,
        0,
        2,
        TimeUnit.SECONDS
    )
        .flatMap { value ->
            try {
                if (value == 3L) {
                    throw Exception("Exception on D")
                }
                Observable.just((value + 65).toInt().toChar().toString())
            } catch (e: Exception) {
                Observable.empty()
            }
        }

    val letter6StreamPeriod2SecB = Observable.intervalRange(
        0,
        6,
        0,
        2,
        TimeUnit.SECONDS
    )
        .map {
            if (it == 3L) {
                throw Exception("Exception on D")
            } else {
                (it + 65).toInt().toChar().toString()
            }
        }
        .onErrorResumeNext { Observable.empty() }


    fun task2A() {
        Observable.zip(
            letter6StreamPeriod2SecA,
            num7StreamPeriod1Sec,
            { it1, it2 -> "($it1,$it2)" }
        )
            .blockingSubscribe(
                { println("$it") },
                { println("error: $it") }
            )
    }

    fun task2B() {
        Observable.zip(
            letter6StreamPeriod2SecB,
            num7StreamPeriod1Sec,
            { it1, it2 -> "($it1,$it2)" }
        )
            .blockingSubscribe(
                { println("$it") },
                { println("error: $it") }
            )
    }
}