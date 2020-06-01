package com.gavincode.patterns.behavioral

sealed class NotifyType{
    class CheckBox(val name: String, val isChecked: Boolean): NotifyType()
    class RadioButton(val name: String, val selected: Int): NotifyType()
    class Button(val name: String): NotifyType()
}

interface Mediator {
    fun notify(notifyType: NotifyType)
}

class Dialog: Mediator {
    val checkBox = CheckBox("myCheckBox", this)
    val radioButton = RadioButton("myRadioButton", this)
    val button = DialogButton("myButton", this)

    override fun notify(notifyType: NotifyType) {
        when(notifyType) {
            is NotifyType.CheckBox -> {
                println("${notifyType.name} is checked ${notifyType.isChecked}")
            }
            is NotifyType.RadioButton -> {
                println("${notifyType.name} is selected ${notifyType.selected}")
            }
            is NotifyType.Button -> {
                println("${notifyType.name} clicked")
            }
        }
    }
}

open class Component(name: String, mediator: Mediator)

class DialogButton(
    private val name: String, private val mediator: Mediator
): Component(name, mediator) {
    fun onClick() {
        mediator.notify(NotifyType.Button(name))
    }
}

class RadioButton(
    private val name: String, private val mediator: Mediator
): Component(name, mediator) {
    fun select(num: Int) {
        mediator.notify(NotifyType.RadioButton(name, num))
    }
}

class CheckBox(
    private val name: String, private val mediator: Mediator
): Component(name, mediator) {
    fun onCheck(check: Boolean) {
        mediator.notify(NotifyType.CheckBox(name, check))
    }
}

fun main() {
    val dialog = Dialog()
    dialog.checkBox.onCheck(true)
    dialog.radioButton.select(2)
    dialog.button.onClick()
}