package com.gavincode.patterns.behavioral

abstract class Downloader {
    abstract val url: String
    private var percentage = 0

    fun startDownload() {
        println("Start download $url")
        while (percentage < 100) {
            percentage += 10
            onPercentage(percentage)
        }
        onDone()
    }

    protected abstract fun onPercentage(percentage: Int)
    protected abstract fun onDone()
}

class VideoDownloader(override val url: String) : Downloader() {

    override fun onDone() {
        println("$url done")
    }

    override fun onPercentage(percentage: Int) {
        println("Downloading..... $percentage%")
    }
}

fun main() {
    val videoDownloader = VideoDownloader("https://video")
    videoDownloader.startDownload()
}