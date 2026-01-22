package com.sinoptik_.rxpractice.domain


import com.sinoptik_.empracticelibrary.data.model.articles
import io.reactivex.rxjava3.core.Observable


data class User(val id: Int, val name: String)

class RemoteDataUC {
    fun execute()=Observable.just(articles)
        //.delay(4, TimeUnit.SECONDS,Schedulers.io())
}