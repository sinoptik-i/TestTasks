package com.sinoptik_.room.task_flowers_12

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sinoptik_.room.task_flowers_12.entity.Bouquet
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent
import com.sinoptik_.room.task_flowers_12.entity.BouquetRecipe
import com.sinoptik_.room.task_flowers_12.entity.Flower
import kotlin.collections.find

@Dao
interface FlowerShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<BouquetComponent>)

    //flowers------------------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlowers(flowers: List<Flower>)

    @Query("SELECT * FROM flowers")
    suspend fun getFlowers(): List<Flower>

    @Query("SELECT * FROM flowers WHERE id = :flowerId")
    suspend fun getFlowerById(flowerId: Long): Flower?

    @Query("UPDATE flowers SET count = :newCount WHERE id = :flowerId")
    suspend fun updateFlowerCount(flowerId: Long, newCount: Int)

    //bouquets------------------------------------------------------------------------------
    @Transaction
    @Query("SELECT * FROM bouquets")
    suspend fun getBouquetRecipes(): List<BouquetRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBouquet(bouquet: Bouquet): Long

}