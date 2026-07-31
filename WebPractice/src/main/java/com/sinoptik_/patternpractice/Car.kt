package com.sinoptik_.patternpractice

class Car private constructor(
    val brand: String,
    val model: String,
    val year: Int,
    val hp: Int
) : Transport {
    private val tax: String = calculateTax()

    private fun calculateTax() = when {
        hp <= 0 -> "Unknown"
        hp <= 100 -> "${hp * 20}"
        hp <= 150 -> "${hp * 30}"
        hp <= 250 -> "${hp * 75}  "
        else -> "${hp * 150}     "
    }


    class Builder(
        private val brand: String,
        private val model: String
    ) : TransportBuilder {
        private var year: Int = 0
        private var hp: Int = 0

        fun setYear(year: Int) = apply { this.year = year }

        fun setHp(hp: Int) = apply {
            this.hp = hp
        }

        override fun build() = Car(brand, model, year, hp)
    }

    override fun toString() = "Car(brand=$brand, model=$model, year=$year, hp=$hp, tax=$tax)"
    override fun move() = println("ratata")

}