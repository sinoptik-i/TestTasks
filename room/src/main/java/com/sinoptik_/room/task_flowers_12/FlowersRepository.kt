package com.sinoptik_.room.task_flowers_12

import com.sinoptik_.room.task_flowers_12.entity.BouquetRecipe
import com.sinoptik_.room.task_flowers_12.entity.Flower
import com.sinoptik_.room.task_flowers_12.init.StartData
import javax.inject.Inject

interface FlowersRepository {
    suspend fun getFlowers(): List<Flower>
    suspend fun getBouquetRecipes(): List<BouquetRecipe>
    suspend fun getAvailableBouquetRecipes(): List<BouquetRecipe>
    suspend fun purchaseBouquet(bouquetId: Long): Boolean
    suspend fun createDb()
}

class FlowersRepositoryImpl @Inject constructor(
    private val dao: FlowerShopDao
) : FlowersRepository {
    private suspend fun safeDao(): FlowerShopDao{
     //  DbInit.isReady.await()
        return dao
    }

    override suspend fun getFlowers() = safeDao().getFlowers()

    override suspend fun getBouquetRecipes() = safeDao().getBouquetRecipes()

    private suspend fun checkBouquetAvailability(targetBouquet: BouquetRecipe): Boolean {
        for (component in targetBouquet.components) {
            val flower = safeDao().getFlowerById(component.flowerId) ?: return false
            if (flower.count < component.countInBouquet) {
                return false
            }
        }
        return true
    }

    override suspend fun getAvailableBouquetRecipes() = safeDao().getBouquetRecipes()
        .filter {
            checkBouquetAvailability(it)
        }

    override suspend fun purchaseBouquet(bouquetId: Long): Boolean {
        val bouquets = getBouquetRecipes()
        val targetBouquet = bouquets.find { it.bouquet.id == bouquetId } ?: return false

        if (!checkBouquetAvailability(targetBouquet))
            return false
        for (component in targetBouquet.components) {
            val flower = safeDao().getFlowerById(component.flowerId)!!
            val newCount = flower.count - component.countInBouquet
            safeDao().updateFlowerCount(component.flowerId, newCount)
        }
        return true
    }


    override suspend fun createDb() = StartData.populateDatabase(safeDao())
}