package com.gavincode.patterns.creational.builder

class Dialog private constructor(
    private val title: String?,
    private val content: String?,
    private val confirmText: String?,
    private val cancelText: String?
) {

    class Builder {
        private var title: String? = null
        private var content: String? = null
        private var confirmText: String? = null
        private var cancelText: String? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setConfirmText(confirmText: String): Builder {
            this.confirmText= confirmText
            return this
        }

        fun setCancelText(cancelText: String): Builder {
            this.cancelText = cancelText
            return this
        }

        fun build(): Dialog {
            return Dialog(title, content, confirmText, cancelText)
        }
    }

    override fun toString(): String {
        return "Dialog(title=$title, content=$content, confirmText=$confirmText, cancelText=$cancelText)"
    }
}

fun main() {
    val dialog = Dialog.Builder()
        .setTitle("Title")
        .setContent("Hello")
        .setConfirmText("OK")
        .setCancelText("CANCEL")
        .build()
    println(dialog)
}