package com.sinoptik_.room.task_flowers_12

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.sinoptik_.room.task_flowers_12.entity.Bouquet
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent
import com.sinoptik_.room.task_flowers_12.entity.BouquetRecipe
import com.sinoptik_.room.task_flowers_12.entity.BouquetRecipeFWC
import com.sinoptik_.room.task_flowers_12.entity.Flower
import com.sinoptik_.room.task_flowers_12.entity.FlowerWithCount

@Dao
interface FlowerShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComponents(components: List<BouquetComponent>)

    //flowers------------------------------------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlowers(flowers: List<Flower>)

    @Query("SELECT * FROM flowers")
    suspend fun getFlowers(): List<Flower>

    @Query("SELECT * FROM flowers WHERE ${Flower.FLOWER_ID} = :flowerId")
    suspend fun getFlowerById(flowerId: Long): Flower?

    @Query("UPDATE flowers SET count = :newCount WHERE ${Flower.FLOWER_ID} = :flowerId")
    suspend fun updateFlowerCount(flowerId: Long, newCount: Int)

    //bouquets------------------------------------------------------------------------------
    @Transaction
    @Query("SELECT * FROM bouquets")
    suspend fun getBouquetRecipes(): List<BouquetRecipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBouquet(bouquet: Bouquet): Long

    @Query("SELECT bouquets.bouquet_id FROM bouquets")
    suspend fun getBouquetsIds():List<Int>

    @Query("SELECT * FROM bouquets WHERE bouquet_id = :bouquetId")
    suspend fun getBouquetById(bouquetId: Int): Bouquet

    @Query(
        """
        SELECT flowers.flower_name, bouquet_components.countInBouquet FROM flowers
        INNER JOIN bouquet_components ON bouquet_components.flower_id_component = flowers.flower_id
         WHERE  bouquet_components.bouquet_id_component = :bouquetId
        """
    )
    suspend fun getFWCsByBouquetId(bouquetId: Int): List<FlowerWithCount>


    @Transaction
    @Query("SELECT * FROM bouquets")
    suspend fun getBouquetRecipeFWC(bouquetId: Int): BouquetRecipeFWC {
        val bouquet = getBouquetById(bouquetId)
        val fWCs = getFWCsByBouquetId(bouquetId)
        return BouquetRecipeFWC(bouquet, fWCs)
    }

    @Transaction
//    @Query("SELECT * FROM bouquets")
    suspend fun getBouquetRecipesFWC(): List<BouquetRecipeFWC> {
        val bouquetIds=getBouquetsIds()
        return bouquetIds.map {
            getBouquetRecipeFWC(it)
        }
    }



}