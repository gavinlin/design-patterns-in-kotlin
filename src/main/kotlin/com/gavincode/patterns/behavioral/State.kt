package com.gavincode.patterns.behavioral

sealed class UiState {
    object Idle: UiState()
    object Loading: UiState()
    class Done(val data: String): UiState()
}

class UI {
    private var state: UiState = UiState.Idle

    fun showData() {
        when (val thisState = state) {
            is UiState.Done -> {
                println("Show: ${thisState.data}")
            }
            is UiState.Loading -> {
                println("Loading, please be patient")
            }
            is UiState.Idle -> {
                println("Call fetch to update state")
            }
        }
    }

    fun fetch() {
        state = UiState.Loading
    }

    fun done(remoteData: String) {
        state = UiState.Done(remoteData)
    }
}

fun main() {
    val ui = UI()

    ui.showData()
    ui.fetch()
    ui.showData()
    ui.done("Done")
    ui.showData()
}