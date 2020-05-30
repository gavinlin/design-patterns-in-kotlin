package com.gavincode.patterns.structural

data class ListViewData(val title: String, val content: String)

class ListView {
    fun showListViewData(list: List<ListViewData>) {
        for (item in list) {
            println(item)
        }
    }
}

/**
 * Remote data is not compatible with LiveViewData
 */
data class RemoteData(val remoteTitle: String, val remoteContent: String)

class ListViewDataAdapter {
    fun toListViewData(remoteData: RemoteData): ListViewData {
        with(remoteData) {
            return ListViewData(
                remoteTitle,
                remoteContent
            )
        }
    }
}

fun main() {
    val listView = ListView()
    val remoteList = listOf(
        RemoteData("Breaking news", "broken news"),
        RemoteData("Hello", "World")
    )
//    listView.showListViewData(remoteList) // error
    val listViewDataAdapter = ListViewDataAdapter()
    listView.showListViewData(remoteList.map { listViewDataAdapter.toListViewData(it) })
}

