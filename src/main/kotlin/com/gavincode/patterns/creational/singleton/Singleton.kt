package com.gavincode.patterns.creational.singleton

object Counter {
    private var count = 0

    fun count(): Int {
        return ++count
    }
}

fun main() {
    println(Counter.count())
    println(Counter.count())
}