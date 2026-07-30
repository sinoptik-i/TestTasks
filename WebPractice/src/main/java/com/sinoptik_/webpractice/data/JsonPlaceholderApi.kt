package com.sinoptik_.webpractice.data

import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}