package com.sinoptik_.room.task_flowers_12

class TestDb(private val repo: FlowersRepository) {

    suspend fun printAllFlowers() {
        println("-----------------------------------------------------")
        println("ALL FLOWERS:")
        repo.getFlowers().forEach {
            println(it)
        }
    }


    suspend fun printUsedFlowers(count: Int = 3) {
        println("-----------------------------------------------------")
        println("USED FLOWERS:")
        repo.getFlowers().take(count).forEach {
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

    suspend fun test() {
        printAllFlowers()
        printBouquets()
        printAvailableBouquets()
        repeat(4) {
            repo.purchaseBouquet(2)
        }
        println("-----------------------------------------------------")
        println("TRY BOUGHT 4 BOUQUETS ID=2")
        printAllFlowers()
        printUsedFlowers()
        printBouquets()
        printAvailableBouquets()
    }

}