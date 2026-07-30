package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class BouquetRecipe(
    @Embedded val bouquet: Bouquet,
    @Relation(
        parentColumn = Bouquet.BOUQUET_ID,
        entityColumn = BouquetComponent.BOUQUET_ID_COMPONENT
    )
    val components: List<BouquetComponent>
)

data class BouquetRecipeFWC(
//    @Embedded
    val bouquet: Bouquet,
/*
    @Relation(
        entity = Flower::class,
        parentColumn = Bouquet.BOUQUET_ID,
        entityColumn = BouquetComponent.BOUQUET_ID_COMPONENT
    )
*/
    val components: List<FlowerWithCount>
)


data class FlowerWithCount(
    @ColumnInfo(name = Flower.FLOWER_NAME)
    val name: String,
    val countInBouquet: Int
)