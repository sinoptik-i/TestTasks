package com.sinoptik_.effectivemobile.di.test

import android.util.Log
import dagger.Module
import javax.inject.Inject

const val TAG_CAR="TAG_CAR"
interface Car {

    fun drive()
}

class  Car1 @Inject constructor() : Car {
    override fun drive() {
        Log.d(TAG_CAR,"drive ${this.javaClass.simpleName}")
    }
}

class Car2 @Inject constructor(): Car {
    override fun drive() {
        Log.d(TAG_CAR,"drive ${this.javaClass.simpleName}")
    }
}

class Car3 @Inject constructor(): Car {
    override fun drive() {
        Log.d(TAG_CAR,"drive ${this.javaClass.simpleName}")
    }
}

