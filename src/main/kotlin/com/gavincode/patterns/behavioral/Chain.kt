package com.gavincode.patterns.behavioral

class TouchEvent

abstract class View(private val children: List<View>) {
    fun handleTouchEvent(event: TouchEvent): Boolean {
        if (onEvent(event)) {
            return true
        }
        for (child in children) {
            if (child.handleTouchEvent(event)) {
                return true
            }
        }
        return false
    }

    abstract fun onEvent(event: TouchEvent): Boolean
}

class TextView(private val children: List<View>) : View(children) {
    override fun onEvent(event: TouchEvent): Boolean {
        println("I am TextView, I don't want to handle the event")
        return false
    }
}

class Button(private val children: List<View>): View(children) {
    override fun onEvent(event: TouchEvent): Boolean {
        println("I am Button, Let me handle the event")
        return true
    }
}

fun main() {
    val chain = TextView(listOf(TextView(listOf(Button(listOf(TextView(listOf())))))))

    chain.handleTouchEvent(TouchEvent())
}

