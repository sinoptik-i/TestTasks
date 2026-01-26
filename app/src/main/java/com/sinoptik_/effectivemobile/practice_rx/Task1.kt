package com.sinoptik_.effectivemobile.practice_rx

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

class Task1 {

    @SuppressLint("CheckResult")
    fun task1() {
        Observable.timer(10, TimeUnit.MILLISECONDS, Schedulers.newThread())
            .subscribeOn(Schedulers.io())//не сработает, т к в таймере указан встроенный шедулер
            .map {
                println("1 ${Thread.currentThread().name}")// newThread
            }
            .doOnSubscribe {// выведется 1, срабатывают снизу вверх по очереди
                println("2 ${Thread.currentThread().name}")// computation, на него влияет ближайший снизу subscribeOn
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.single())
            .flatMap {
                println("3 ${Thread.currentThread().name}")// single, на него влияет observeOn сверху вниз
                Observable.just(it)
                    .subscribeOn(Schedulers.io())
            }
            .subscribe {
                println("4 ${Thread.currentThread().name}")//io, т к это новый Observable, порожденный flatMap
            }
        Thread.sleep(1000)
    }
    /*2 RxComputationThreadPool-1
     1 RxNewThreadScheduler-1
     3 RxSingleScheduler-1
     4 RxCachedThreadScheduler-1*/

    @SuppressLint("CheckResult")
    fun task121() {
        val subject = PublishSubject.create<String>()
        subject.subscribe {
            println(it)
        }
        subject.onNext("1")
        subject.onNext("2")
        subject.onNext("3")
    }

    @SuppressLint("CheckResult")
    fun task122() {
        val subject = ReplaySubject.create<String>()
        subject.onNext("1")
        subject.onNext("2")
        subject.onNext("3")
        subject.subscribe {
            println(it)
        }
    }

}