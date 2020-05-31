package com.gavincode.patterns.structural

interface Device {
    var enabled: Boolean
    var volume: Int
    var channel: Int
    fun printStatus()
}

class Radio : Device {
    override var enabled: Boolean = false
    override var volume: Int = 30
        set(value) {
            field = when {
                value > 100 -> 100
                value < 0 -> 0
                else -> value
            }
        }
    override var channel: Int = 1

    override fun printStatus() {
        println("--------------------------")
        println("| I'm radio.")
        println("| I'm ${if (enabled) "enabled" else "disable"}")
        println("| Current volume is $volume")
        println("| Current channel is $channel")
        println("--------------------------")
    }
}

class Tv: Device {
    override var enabled: Boolean = false
    override var volume: Int = 30
        set(value) {
            field = when {
                value > 100 -> 100
                value < 0 -> 0
                else -> value
            }
        }
    override var channel: Int = 1

    override fun printStatus() {
        println("--------------------------")
        println("| I'm TV.")
        println("| I'm ${if (enabled) "enabled" else "disable"}")
        println("| Current volume is $volume")
        println("| Current channel is $channel")
        println("--------------------------")
    }
}

interface Remote {
    fun power()
    fun volumeDown()
    fun volumeUp()
    fun channelDown()
    fun channelUp()
}

open class BasicRemote(
    private val device: Device
): Remote {

    override fun power() {
        device.enabled = !device.enabled
    }

    override fun volumeDown() {
        device.volume = device.volume - 10
    }

    override fun volumeUp() {
        device.volume = device.volume + 10
    }

    override fun channelDown() {
        device.channel = device.channel - 1
    }

    override fun channelUp() {
        device.channel = device.channel + 1
    }
}

class AdvancedRemote(
    private val device: Device
): BasicRemote(device) {
    fun mute() {
        device.volume = 0
    }
}

fun main() {
    val device: Device = Tv()
    val advancedRemote = AdvancedRemote(device)
    advancedRemote.mute()
    advancedRemote.channelUp()
    device.printStatus()
}
