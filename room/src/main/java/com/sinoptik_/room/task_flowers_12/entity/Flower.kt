package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class Flower(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FLOWER_ID)
    val id: Long = 0,

    @ColumnInfo(name = FLOWER_NAME)
    val name: String,
    val count: Int,
    val country: String = "TERRA INCOGNITA"
) {
    companion object {
        const val FLOWER_ID = "flower_id"
        const val FLOWER_NAME = "flower_name"
    }
}