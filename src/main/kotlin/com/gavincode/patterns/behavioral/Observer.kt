package com.gavincode.patterns.behavioral

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

fun main() {
    val weatherReport = WeatherReport()
    val emailReceiver = EmailReceiver()
    val tvStation = TVStation()
    weatherReport.register(emailReceiver)
    weatherReport.register(tvStation)

    weatherReport.newReport("Cloudy, Temperature: 23 degrees")

    weatherReport.unregister(emailReceiver)
    weatherReport.unregister(tvStation)
}