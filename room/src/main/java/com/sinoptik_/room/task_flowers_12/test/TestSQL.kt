package com.sinoptik_.room.task_flowers_12.test

import androidx.sqlite.db.SimpleSQLiteQuery
import com.sinoptik_.room.task_flowers_12.FlowersDb
import com.sinoptik_.room.task_flowers_12.FlowersRepositoryImpl
import com.sinoptik_.room.task_flowers_12.entity.Bouquet
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent
import com.sinoptik_.room.task_flowers_12.init.StartData
import kotlinx.coroutines.runBlocking

class TestSql(private val db: FlowersDb) {

    private var testDB: TestDb = TestDb(FlowersRepositoryImpl(db.dao()))
    private val sqliteDb = db.openHelper.writableDatabase


    init {
        runBlocking {
            StartData.populateDatabase(db.dao())
            testDB.printBouquets()
            printComponents()
            println("-----------------------------------------------------")
        }
    }

    fun testInnerJoin() = runBlocking {

        val innerJoinSql = """
            SELECT bouquets.name, bouquets.${Bouquet.BOUQUET_ID}, bouquet_components.count FROM bouquets
            INNER JOIN bouquet_components ON bouquets.${Bouquet.BOUQUET_ID} = bouquet_components.${BouquetComponent.BOUQUET_ID_COMPONENT}
        """.trimIndent()

        val innerResults = mutableListOf<String>()
        sqliteDb.query(SimpleSQLiteQuery(innerJoinSql)).use { c ->
            while (c.moveToNext()) innerResults.add(
                "Name: ${c.getString(0)}" +
                        " | bouquets.${Bouquet.BOUQUET_ID}: ${c.getString(1)}" +
                        " | count: ${c.getInt(2)}"
            )
        }
        innerResults.forEach { println(it) }
    }

    suspend fun printComponents() {
        println("-----------------------------------------------------")
        println("ALL COMPONENTS:")

        val query = """
            SELECT * FROM bouquet_components
        """.trimIndent()

        val result = mutableListOf<BouquetComponent>()
        sqliteDb.query(SimpleSQLiteQuery(query)).use { c ->
            while (c.moveToNext()) {
                val bouquetId = c.getLong(c.getColumnIndexOrThrow(BouquetComponent.BOUQUET_ID_COMPONENT))
                val flowerId = c.getLong(c.getColumnIndexOrThrow(BouquetComponent.FLOWER_ID_COMPONENT))
                val count = c.getInt(c.getColumnIndexOrThrow("countInBouquet"))

                result.add(
                    BouquetComponent(
                        bouquetId = bouquetId,
                        flowerId = flowerId,
                        countInBouquet = count
                    )
                )
            }
        }
        result.forEach { println(it) }
    }




}