Design Patterns implemented in Kotlin
=====================================

## Table of Contents

|[Creational](#creational)|[Structural](#structural)|[Behavioral](#behavioral)|
|-------------------------|-------------------------|-------------------------|
|[Factory Method](#factory-method)|Adapter|Strategy|
|[Abstract Factory](#abstract-factory)|||
|[Builder](#builder)|||

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

Builder
-------

Example:
```kotlin
class Dialog private constructor(
    private val title: String?,
    private val content: String?,
    private val confirmText: String?,
    private val cancelText: String?
) {

    class Builder {
        private var title: String? = null
        private var content: String? = null
        private var confirmText: String? = null
        private var cancelText: String? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setConfirmText(confirmText: String): Builder {
            this.confirmText= confirmText
            return this
        }

        fun setCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }

        fun build(): Dialog {
            return Dialog(title, content, confirmText, cancelText)
        }
    }

    override fun toString(): String {
        return "Dialog(title=$title, content=$content, confirmText=$confirmText, cancelText=$cancelText)"
    }
}
```

Usage:
```kotlin
    val dialog = Dialog.Builder()
        .setTitle("Title")
        .setContent("Hello")
        .setConfirmText("OK")
        .setCancelText("CANCEL")
        .build()
    println(dialog)
```

Result:
```shell script
Dialog(title=Title, content=Hello, confirmText=OK, cancelText=CANCEL)
```

Structural
==========

Behavioral
==========

Strategy
--------
Example:
```kotlin
Dialog(title=Title, content=Hello, confirmText=OK, cancelText=CANCEL)
```
Usage:
```kotlin
    println("Sum even numbers ${sumWithCondition(list) {it % 2 == 0}}")
    println("Sum odd numbers ${sumWithCondition(list) {it % 2 != 0}}")
    println("Sum all numbers ${sumWithCondition(list) { true }}")
```
Result:
```shell script
Sum even numbers 20
Sum odd numbers 16
Sum all numbers 36
```


