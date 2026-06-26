package com.sinoptik_.room

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sinoptik_.room.task_flowers_12.FlowerShopDao
import com.sinoptik_.room.task_flowers_12.FlowerShopDatabaseCallback
import com.sinoptik_.room.task_flowers_12.FlowersDb
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import com.sinoptik_.room.task_flowers_12.FlowersRepositoryImpl
import com.sinoptik_.room.task_flowers_12.StartData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DbTest {
    private lateinit var db: FlowersDb
    private lateinit var dao: FlowerShopDao
    private lateinit var repo: FlowersRepository
    private val scope = TestScope()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FlowersDb::class.java
        )
//            .addCallback(
//                FlowerShopDatabaseCallback(
//                    scope,
//                    { db })
//            )
            .allowMainThreadQueries()
            .build()
        dao = db.dao()
        repo = FlowersRepositoryImpl(dao)


    }

    @After
    fun closeDb() {
        db.close()
    }

    suspend fun printFlowers() {
        println("-----------------------------------------------------")
        println("USED FLOWERS:")
        repo.getFlowers().take(3).forEach {
            println(it)
        }
    }

    suspend fun printBouquets() {
        println("-----------------------------------------------------")
        println("ALL BOUQUETS:")
        repo.getBouquetRecipes().forEach {
            println(it)
        }
    }

    suspend fun printAvailableBouquets() {
        println("-----------------------------------------------------")
        println("AVAILABLE BOUQUETS:")
        repo.getAvailableBouquetRecipes().forEach {
            println(it)
        }
    }


    @Test
    fun test() = runTest {
        StartData.populateDatabase(dao)
        printFlowers()
        printBouquets()
        printAvailableBouquets()
        repeat(4) {
            repo.purchaseBouquet(2)
        }
        println("-----------------------------------------------------")
        println("BOUGHT 4 BOUQUETS ID=2")
        printFlowers()
        printBouquets()
        printAvailableBouquets()
    }
}