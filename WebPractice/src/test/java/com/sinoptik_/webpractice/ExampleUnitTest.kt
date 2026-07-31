package com.sinoptik_.webpractice

import com.sinoptik_.patternpractice.Car
import com.sinoptik_.patternpractice.CarFactory
import com.sinoptik_.patternpractice.TransportFactory
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val car=Car.Builder("bmw","e24")
            .build()
        println(car)
        val car2=Car.Builder("bmw","e24")
            .setHp(200)
            .setYear(1987)
            .build()
        println(car2)

        val factory: TransportFactory= CarFactory()
        val carBuilder=Car.Builder("audi","c4")
            .setHp(115)
            .setYear(1991)
        val car3=factory.createTransport(carBuilder)
        println(car3)

    }
}