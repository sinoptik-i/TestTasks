package com.sinoptik_.room.task_flowers_12

import com.sinoptik_.room.task_flowers_12.entity.BouquetRecipe
import com.sinoptik_.room.task_flowers_12.entity.Flower

interface FlowersRepository {
    suspend fun getFlowers(): List<Flower>
    suspend fun getBouquetRecipes(): List<BouquetRecipe>
    suspend fun getAvailableBouquetRecipes(): List<BouquetRecipe>
    suspend fun purchaseBouquet(bouquetId: Long): Boolean
    suspend fun createDb()
}

class FlowersRepositoryImpl(
    private val dao: FlowerShopDao
) : FlowersRepository {
    override suspend fun getFlowers() = dao.getFlowers()

    override suspend fun getBouquetRecipes() = dao.getBouquetRecipes()

    private suspend fun checkBouquetAvailability(targetBouquet: BouquetRecipe): Boolean {
        for (component in targetBouquet.components) {
            val flower = dao.getFlowerById(component.flowerId) ?: return false
            if (flower.count < component.count) {
                return false
            }
        }
        return true
    }

    override suspend fun getAvailableBouquetRecipes() = dao.getBouquetRecipes()
        .filter {
            checkBouquetAvailability(it)
        }

    override suspend fun purchaseBouquet(bouquetId: Long): Boolean {
        val bouquets = getBouquetRecipes()
        val targetBouquet = bouquets.find { it.bouquet.id == bouquetId } ?: return false

        if (!checkBouquetAvailability(targetBouquet))
            return false
        for (component in targetBouquet.components) {
            val flower = dao.getFlowerById(component.flowerId)!!
            val newCount = flower.count - component.count
            dao.updateFlowerCount(component.flowerId, newCount)
        }
        return true
    }


    override suspend fun createDb() = StartData.populateDatabase(dao)
}