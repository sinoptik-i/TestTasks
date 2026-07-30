package com.sinoptik_.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sinoptik_.room.task_flowers_12.FlowerShopDao
import com.sinoptik_.room.task_flowers_12.FlowersDb
import com.sinoptik_.room.task_flowers_12.FlowersRepository
import com.sinoptik_.room.task_flowers_12.FlowersRepositoryImpl
import com.sinoptik_.room.task_flowers_12.test.TestDb
import com.sinoptik_.room.task_flowers_12.init.StartData
import com.sinoptik_.room.task_flowers_12.test.TestSql
import kotlinx.coroutines.test.TestScope
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
    private lateinit var testDB: TestDb
    private lateinit var testSql: TestSql

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FlowersDb::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = db.dao()
        repo = FlowersRepositoryImpl(dao)
        testDB = TestDb(repo)
        testSql= TestSql(db)

    }
    @After
    fun closeDb() {
        db.close()
    }


    @Test
    fun test() = runTest {
        StartData.populateDatabase(dao)
        testDB.test()
    }

    @Test
    fun testSql()=testSql.testInnerJoin()

    @Test
    fun testPrintBouquets()=runTest {
        dao.getBouquetRecipesFWC().forEach {
            println(it)
        }
    }
}

