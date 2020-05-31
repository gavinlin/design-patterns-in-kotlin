package com.gavincode.patterns.structural

interface View {
    var x: Int
    var y: Int
    fun draw()
}

class LineView(override var x: Int, override var y: Int) : View {
    override fun draw() {
        println("Draw line to $x, $y")
    }
}

class TextView(override var x: Int, override var y: Int,
val text: String): View {

    override fun draw() {
        println("Draw text $text to $x, $y")
    }
}

class ViewGroup(
    override var x: Int,
    override var y: Int,
    private val children: List<View>
): View {

    override fun draw() {
        println("Draw view group to $x, $y")
        for (view in children) {
            view.draw()
        }
    }
}

fun main() {
    val firstViewGroup = ViewGroup(1, 0,
        listOf(LineView(10, 10), TextView(10, 20, "Hello"))
        )

    val secondViewGroup = ViewGroup(2, 0,
        listOf(firstViewGroup, TextView(30, 30, "World"))
    )

    secondViewGroup.draw()
}
