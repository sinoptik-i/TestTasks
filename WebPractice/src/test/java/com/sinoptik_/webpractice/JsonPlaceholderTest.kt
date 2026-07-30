package com.sinoptik_.webpractice

import com.sinoptik_.webpractice.data.JPHRetrofitClient
import kotlinx.coroutines.runBlocking
import org.junit.Test

class JsonPlaceholderTest {

    @Test
    fun testFetchPosts() = runBlocking {
        try {
            val response = JPHRetrofitClient.api.getPosts()

            if (response.isSuccessful && response.body() != null) {
                val postsList = response.body()!!
                println("Count: ${postsList.size}")
                println("headers: ${response.headers()}")
                println("First: ${postsList.firstOrNull()?.toString()}")
            } else {
                println("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            println("Error: ${e.localizedMessage}")
        }
    }
}