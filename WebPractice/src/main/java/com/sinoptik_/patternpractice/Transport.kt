package com.sinoptik_.patternpractice

interface Transport{
    fun move()
}

class Bus: Transport {
    override fun move() =println("phh")
}