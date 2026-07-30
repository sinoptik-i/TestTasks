package com.sinoptik_.room.task_flowers_12.init

import com.sinoptik_.room.task_flowers_12.FlowerShopDao
import com.sinoptik_.room.task_flowers_12.entity.Bouquet
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent
import com.sinoptik_.room.task_flowers_12.entity.Flower

object StartData {
    suspend fun populateDatabase(dao: FlowerShopDao) {
        val defaultFlowers = listOf(
            Flower(id = 1, name = "Роза красная", count = 50),
            Flower(id = 2, name = "Роза белая", count = 30),
            Flower(id = 3, name = "Тюльпан", count = 100),
            Flower(id = 4, name = "Хризантема", count = 40),
            Flower(id = 5, name = "Пион", count = 25),
            Flower(id = 6, name = "Лилия", count = 15),
            Flower(id = 7, name = "Гвоздика", count = 60),
            Flower(id = 8, name = "Орхидея", count = 10),
            Flower(id = 9, name = "Нарцисс", count = 45),
            Flower(id = 10, name = "Ромашка", count = 80)
        )
        dao.insertFlowers(defaultFlowers)

        val mixBouquetId = dao.insertBouquet(Bouquet(id = 1, name = "Весенний Микс"))
        val roseBouquetId = dao.insertBouquet(Bouquet(id = 2, name = "Моно-Букет из роз"))

        val components = listOf(
            BouquetComponent(bouquetId = mixBouquetId, flowerId = 2, countInBouquet = 3),
            BouquetComponent(bouquetId = mixBouquetId, flowerId = 3, countInBouquet = 2),
            BouquetComponent(bouquetId = roseBouquetId, flowerId = 1, countInBouquet = 11)
        )
        dao.insertComponents(components)
    }
}
