package com.gavincode.patterns.behavioral

interface Command {
    fun execute()
}

class EditorService {
    fun copy() {
        println("Copy text")
    }

    fun cut() {
        println("Cut text")
    }

    fun paste() {
        println("Paste text")
    }
}

class CopyCommand(private val editorService: EditorService): Command {
    override fun execute() {
        editorService.copy()
    }
}

class PasteCommand(private val editorService: EditorService): Command {
    override fun execute() {
        editorService.paste()
    }
}

class CutCommand(private val editorService: EditorService): Command {
    override fun execute() {
        editorService.cut()
    }

}

typealias OnClickListener = () -> Unit
class EditorButton(
    private val onClickListener: OnClickListener
) {
    fun click() {
        onClickListener.invoke()
    }
}

class EditorGui(private val editorService: EditorService) {
    val copyButton = EditorButton {
        CopyCommand(editorService).execute()
    }
    val pasteButton = EditorButton {
       PasteCommand(editorService).execute()
    }
    val cutButton = EditorButton {
        CutCommand(editorService).execute()
    }
}

fun main() {
    val editorGui = EditorGui(EditorService())

    editorGui.copyButton.click()
    editorGui.pasteButton.click()
    editorGui.cutButton.click()
}