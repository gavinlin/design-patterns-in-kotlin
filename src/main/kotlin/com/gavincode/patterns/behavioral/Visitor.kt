package com.gavincode.patterns.behavioral

import java.text.DecimalFormat

interface Visitor {
    fun visit(liquor: Liquor): Double
    fun visit(tobacco: Tobacco): Double
    fun visit(necessity: Necessity): Double
}

interface Visitable {
    fun accept(visitor: Visitor): Double
}

abstract class Item {
    abstract val price: Double
}

class Liquor(override val price: Double) : Item(), Visitable {
    override fun accept(visitor: Visitor): Double {
        return visitor.visit(this)
    }
}
class Tobacco(override val price: Double) : Item(), Visitable {
    override fun accept(visitor: Visitor): Double {
        return visitor.visit(this)
    }
}

class Necessity(override val price: Double) : Item(), Visitable {
    override fun accept(visitor: Visitor): Double {
        return visitor.visit(this)
    }
}

class TaxVisitor: Visitor {
    private val df = DecimalFormat("#.##")

    override fun visit(liquor: Liquor): Double {
        return df.format(liquor.price * .18 + liquor.price).toDouble()
    }

    override fun visit(tobacco: Tobacco): Double {
        return df.format(tobacco.price * .32 + tobacco.price).toDouble()
    }

    override fun visit(necessity: Necessity): Double {
        return df.format(necessity.price * .01 + necessity.price).toDouble()
    }
}

fun main() {
    val taxVisitor = TaxVisitor()

    val milk = Necessity(2.5)
    println(milk.accept(taxVisitor))
    val cigars = Tobacco(12.0)
    println(cigars.accept(taxVisitor))
}