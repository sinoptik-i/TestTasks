package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BouquetRecipe(
    @Embedded val bouquet: Bouquet,
    @Relation(parentColumn = "id", entityColumn = "bouquetId")
    val components: List<BouquetComponent>
)
