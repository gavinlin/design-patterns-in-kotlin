package com.gavincode.patterns.creational

enum class TransportType {
    TRUCK, SHIP
}

interface Transport {
    fun deliver()
}

class Truck: Transport {
    override fun deliver() {
        println("Deliver by truck")
    }
}

class Ship: Transport {
    override fun deliver() {
        println("Deliver by ship")
    }
}

class LogisticFactory {
    fun createTransport(transportType: TransportType): Transport {
        return when(transportType) {
            TransportType.TRUCK -> Truck()
            TransportType.SHIP -> Ship()
        }
    }
}

fun main() {
    val logisticFactory = LogisticFactory()
    val transport = logisticFactory.createTransport(TransportType.TRUCK)
    transport.deliver()
}