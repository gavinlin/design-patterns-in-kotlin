Design Patterns implemented in Kotlin
=====================================

## Table of Contents

|[Creational](#creational)|[Structural](#structural)|[Behavioral](#behavioral)|
|-------------------------|-------------------------|-------------------------|
|Factory Method|Adapter|Observer|

Creational
==========

Factory Method
--------------
```kotlin
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
```

usage
```kotlin
fun main() {
    val logisticFactory = LogisticFactory()
    val transport = logisticFactory.createTransport(TransportType.TRUCK)
    transport.deliver()
}
```

Result
```shell script
Deliver by truck
```

