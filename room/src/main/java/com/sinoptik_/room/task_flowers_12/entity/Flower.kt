package com.sinoptik_.room.task_flowers_12.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flowers")
data class Flower(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val count: Int
)
