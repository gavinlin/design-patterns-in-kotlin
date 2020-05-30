package com.gavincode.patterns.behavioral

fun sumWithCondition(integers: List<Int>, condition: (a: Int) -> Boolean): Int {
    return integers.filter { condition(it) }.sum()
}

fun main() {
    val list = listOf(1,2,3,4,5,6,7,8)
    println("Sum even numbers ${sumWithCondition(list) {it % 2 == 0}}")
    println("Sum odd numbers ${sumWithCondition(list) {it % 2 != 0}}")
    println("Sum all numbers ${sumWithCondition(list) { true }}")
}