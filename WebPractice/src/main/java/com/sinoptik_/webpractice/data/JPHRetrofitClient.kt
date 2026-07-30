package com.sinoptik_.webpractice.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object JPHRetrofitClient {
    private const val BASE_URL ="https://jsonplaceholder.typicode.com/"

    val api: JsonPlaceholderApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
              .client(LogInterceptor.getClientWithInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JsonPlaceholderApi::class.java)
    }
}