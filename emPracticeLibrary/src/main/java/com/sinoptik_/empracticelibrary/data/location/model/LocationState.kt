package com.sinoptik_.empracticelibrary.data.location.model

data class LocationState(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)