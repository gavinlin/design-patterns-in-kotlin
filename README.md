Design Patterns implemented in Kotlin
=====================================

## Table of Contents

|[Creational](#creational)|[Structural](#structural)|[Behavioral](#behavioral)|
|-------------------------|-------------------------|-------------------------|
|[Factory Method](#factory-method)|Adapter|Observer|
|[Abstract Factory](#abstract-factory)|||

Creational
==========

Factory Method
--------------
Factory Method lets a class defer instantiation to subclasses.

example:

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
    companion object {
        fun createTransport(transportType: TransportType): Transport {
            return when(transportType) {
                TransportType.TRUCK -> Truck()
                TransportType.SHIP -> Ship()
            }
        }
    }
}
```

usage:

```kotlin
fun main() {
    val transport = LogisticFactory.createTransport(TransportType.TRUCK)
    transport.deliver()
}
```

Result:

```shell script
Deliver by truck
```

Abstract Factory
----------------

Example:

```kotlin
enum class TransportType {
    CAR, MOTORBIKE,
    SHIP, BOAT
}

interface Transport {
    fun deliver()
}

class Car: Transport {
    override fun deliver() {
        println("Deliver by car")
    }
}

class MotorBike: Transport {
    override fun deliver() {
        println("Deliver by motorbike")
    }
}

class Ship: Transport {
    override fun deliver() {
        println("Deliver by ship")
    }
}

class Boat: Transport {
    override fun deliver() {
        println("Deliver by boat")
    }
}

interface TransportFactory {
    fun makeTransport(type: TransportType): Transport
}

class RoadTransportFactory: TransportFactory {
    override fun makeTransport(type: TransportType): Transport {
        return when(type) {
            TransportType.CAR -> Car()
            TransportType.MOTORBIKE -> MotorBike()
            else -> throw IllegalArgumentException("Cannot find road transport type")
        }
    }
}

class SeaTransportFactory: TransportFactory {
    override fun makeTransport(type: TransportType): Transport {
        return when(type) {
            TransportType.BOAT -> Boat()
            TransportType.SHIP -> Ship()
            else -> throw IllegalArgumentException("Cannot find sea transport type")
        }
    }
}

class LogisticFactory {
    companion object {
        fun createTransport(type: TransportType): Transport = when (type) {
            TransportType.BOAT,
                TransportType.SHIP -> {
                val factory = SeaTransportFactory()
                factory.makeTransport(type)
            }
            TransportType.CAR,
                TransportType.MOTORBIKE -> {
                val factory = RoadTransportFactory()
                factory.makeTransport(type)
            }
        }
    }
}
```

Usage:

```kotlin
    val carDeliver = LogisticFactory.createTransport(TransportType.CAR)
    carDeliver.deliver()
```

Result:
```shell script
Deliver by car
```