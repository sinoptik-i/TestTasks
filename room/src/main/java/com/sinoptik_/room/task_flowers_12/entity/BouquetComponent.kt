package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "bouquet_components",
    primaryKeys = ["bouquetId", "flowerId"],
    foreignKeys = [
        ForeignKey(
            entity = Bouquet::class,
            parentColumns = ["id"],
            childColumns = ["bouquetId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Flower::class,
            parentColumns = ["id"],
            childColumns = ["flowerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BouquetComponent(
    val bouquetId: Long,
    val flowerId: Long,
    val count: Int
)