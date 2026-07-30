package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent.Companion.BOUQUET_ID_COMPONENT
import com.sinoptik_.room.task_flowers_12.entity.BouquetComponent.Companion.FLOWER_ID_COMPONENT

@Entity(
    tableName = "bouquet_components",
    primaryKeys = [BOUQUET_ID_COMPONENT, FLOWER_ID_COMPONENT],
    foreignKeys = [
        ForeignKey(
            entity = Bouquet::class,
            parentColumns = [Bouquet.BOUQUET_ID],
            childColumns = [BOUQUET_ID_COMPONENT],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Flower::class,
            parentColumns = [Flower.FLOWER_ID],
            childColumns = [FLOWER_ID_COMPONENT],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BouquetComponent(
    @ColumnInfo(name = BOUQUET_ID_COMPONENT)
    val bouquetId: Long,
    @ColumnInfo(name = FLOWER_ID_COMPONENT)
    val flowerId: Long,
    val countInBouquet: Int
){
    companion object{
        const val BOUQUET_ID_COMPONENT="bouquet_id_component"
        const val FLOWER_ID_COMPONENT="flower_id_component"
    }
}