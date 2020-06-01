package com.gavincode.patterns.behavioral

interface Iterator <T> {
    fun hasNext(): Boolean
    fun next(): T
    fun reset()
}

data class Friend(val name: String)

class MyFriends(
    private val friends: Array<Friend>
): Iterator<Friend> {
    private var position = 0

    override fun hasNext(): Boolean {
        return position < friends.size
    }

    override fun next(): Friend {
        return friends[position++]
    }

    override fun reset() {
        position = 0
    }
}

fun main() {
    val myFriends = MyFriends(
        arrayOf(Friend("Tony"), Friend("Tom"), Friend("TT"))
    )

    while (myFriends.hasNext()) {
        println(myFriends.next())
    }
}