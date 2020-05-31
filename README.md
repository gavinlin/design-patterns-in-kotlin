Design Patterns implemented in Kotlin
=====================================

## Table of Contents

|[Creational](#creational)|[Structural](#structural)|[Behavioral](#behavioral)|
|-------------------------|-------------------------|-------------------------|
|[Factory Method](#factory-method)|[Adapter](#adapter)|[Strategy](#strategy)|
|[Abstract Factory](#abstract-factory)| |[Observer](#observer)|
|[Builder](#builder)|||
|[Singleton](#singleton)|||

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

Singleton
---------
[Kotlin object explain](https://www.youtube.com/watch?v=KUk6k865Vgg)

Example:

```kotlin
object Counter {
    private var count = 0

    fun count(): Int {
        return ++count
    }
}
```

Usage:

```kotlin
    println(Counter.count())
    println(Counter.count())
```

Result:

```shell script
1
2
```

Structural
==========

Adapter
------

Example:
```kotlin

data class ListViewData(val title: String, val content: String)

class ListView {
    fun showListViewData(list: List<ListViewData>) {
        for (item in list) {
            println(item)
        }
    }
}

/**
 * Remote data is not compatible with LiveViewData
 */
data class RemoteData(val remoteTitle: String, val remoteContent: String)

class ListViewDataAdapter {
    fun toListViewData(remoteData: RemoteData): ListViewData {
        with(remoteData) {
            return ListViewData(
                remoteTitle,
                remoteContent
            )
        }
    }
}
```

Usage:
```kotlin
    val listView = ListView()
    val remoteList = listOf(
        RemoteData("Breaking news", "broken news"),
        RemoteData("Hello", "World")
    )
//    listView.showListViewData(remoteList) // error
    val listViewDataAdapter = ListViewDataAdapter()
    listView.showListViewData(remoteList.map { listViewDataAdapter.toListViewData(it) })
```

Result:
```shell script
ListViewData(title=Breaking news, content=broken news)
ListViewData(title=Hello, content=World)
```

Behavioral
==========

Strategy
--------
Example:
```kotlin
fun sumWithCondition(integers: List<Int>, condition: (a: Int) -> Boolean): Int {
    return integers.filter { condition(it) }.sum()
}
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

Observer
--------

Example:

```kotlin
interface Observable <T> {
    fun register(observer: Observer<T>)
    fun unregister(observer: Observer<T>)
}

interface Observer<T> {
    fun notify(t: T)
}

class WeatherReport: Observable<String> {
    private val observers: HashSet<Observer<String>> = HashSet()

    fun newReport(report: String) {
        for (observer in observers) {
            observer.notify(report)
        }
    }

    override fun register(observer: Observer<String>) {
        observers.add(observer)
    }

    override fun unregister(observer: Observer<String>) {
        observers.remove(observer)
    }
}

class TVStation: Observer<String> {
    override fun notify(t: String) {
        println("TV station got report: $t")
    }

}
class EmailReceiver: Observer<String> {
    override fun notify(t: String) {
        println("Email receiver got report: $t")
    }
}
```

Usage:

```kotlin
    val weatherReport = WeatherReport()
    val emailReceiver = EmailReceiver()
    val tvStation = TVStation()
    weatherReport.register(emailReceiver)
    weatherReport.register(tvStation)

    weatherReport.newReport("Cloudy, Temperature: 23 degrees")

    weatherReport.unregister(emailReceiver)
    weatherReport.unregister(tvStation)
```

Result:

```shell script
TV station got report: Cloudy, Temperature: 23 degrees
Email receiver got report: Cloudy, Temperature: 23 degrees
```