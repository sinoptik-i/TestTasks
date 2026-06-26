package com.sinoptik_.koinexample.utils

import android.content.Context
import android.util.Log
import com.sinoptik_.koinexample.R


class AnyApi
class AnyApi2


interface Utils {

    fun cryMyName(addedText: String = "") {
        Log.d("UTILS", "${this.javaClass.simpleName} <- it's me, addedText: $addedText")
    }

    fun cryMyNameAsTag() {
        Log.d(this.javaClass.simpleName, "<- it's me")
    }
}


class TestMessagePrinter(private val context: Context) : Utils {
    fun printHello() = "Koin работает! Имя приложения: ${context.getString(R.string.app_name)}"
}

class FormValidator() : Utils {
    fun isValid(text: String) = false
}


class ActivityTracker : Utils
class ServiceLogger(api: AnyApi, private val data: String) : Utils {
    override fun cryMyName(addedText: String) {
        super.cryMyName(data)
    }
}
