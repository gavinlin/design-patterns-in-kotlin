package com.gavincode.patterns.creational.prototype

data class News(val title: String, val content: String)

fun main() {
    val firstNews = News("Breaking", "Broken")
    val secondNews = firstNews.copy()

    println(secondNews)
    println("Are first news and second news the same? ${firstNews === secondNews}")
}