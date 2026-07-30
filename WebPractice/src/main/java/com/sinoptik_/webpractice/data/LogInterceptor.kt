package com.sinoptik_.webpractice.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class StatusCodeInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        println("Code: ${response.code}")
        return response
    }
}



object LogInterceptor {

    fun getClientWithInterceptor(): OkHttpClient {
        val loggingCode= StatusCodeInterceptor()
        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor (loggingCode)
            .build()
        return okHttpClient
    }


}