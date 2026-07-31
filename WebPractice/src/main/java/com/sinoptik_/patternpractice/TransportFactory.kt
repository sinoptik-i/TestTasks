package com.sinoptik_.patternpractice

interface TransportFactory {
    fun createTransport(builder: TransportBuilder): Transport {
        return builder.build()
    }
}

interface TransportBuilder {
    fun build(): Transport
}


class CarFactory : TransportFactory {
    override fun createTransport(builder: TransportBuilder): Transport {
        checkAirBags()
        return super.createTransport(builder)
    }

    private fun checkAirBags() = println("AirBags checked!")
}

class BusFactory : TransportFactory {
    override fun createTransport(builder: TransportBuilder): Transport {
        checkValidator()
        return super.createTransport(builder)
    }

    private fun checkValidator() = println("Validator checked!")
}