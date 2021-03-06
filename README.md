Design Patterns implemented in Kotlin
=====================================

## Table of Contents

|[Creational](#creational)|[Structural](#structural)|[Behavioral](#behavioral)|
|-------------------------|-------------------------|-------------------------|
|[Factory Method](#factory-method)|[Adapter](#adapter)|[Strategy](#strategy)|
|[Abstract Factory](#abstract-factory)|[Bridge](#bridge) |[Observer](#observer)|
|[Builder](#builder)|[Composite](#composite)|[Chain of Responsibility](#chain-of-responsibility)|
|[Singleton](#singleton)|[Decorator](#decorator)|[Command](#command)|
|[Prototype](#prototype) |[Facade](#facade)|[Iterator](#iterator)|
| |[Flyweight](#flyweight)|[Mediator](#mediator)|
| |[Proxy](#proxy)|[Memento](#memento)|
| | |[State](#state)|
| | |[Template method](#template-method)|
| | |[Visitor](#visitor)|

Creational
==========

Factory Method
--------------
Factory Method lets a class defer instantiation to subclasses.

example:

```kotlin
enum class TransportType {
    TRUCK, SHIP
}

interface Transport {
    fun deliver()
}

class Truck: Transport {
    override fun deliver() {
        println("Deliver by truck")
    }
}

class Ship: Transport {
    override fun deliver() {
        println("Deliver by ship")
    }
}

class LogisticFactory {
    companion object {
        fun createTransport(transportType: TransportType): Transport {
            return when(transportType) {
                TransportType.TRUCK -> Truck()
                TransportType.SHIP -> Ship()
            }
        }
    }
}
```

usage:

```kotlin
fun main() {
    val transport = LogisticFactory.createTransport(TransportType.TRUCK)
    transport.deliver()
}
```

Result:

```shell script
Deliver by truck
```

Abstract Factory
----------------

Example:

```kotlin
enum class TransportType {
    CAR, MOTORBIKE,
    SHIP, BOAT
}

interface Transport {
    fun deliver()
}

class Car: Transport {
    override fun deliver() {
        println("Deliver by car")
    }
}

class MotorBike: Transport {
    override fun deliver() {
        println("Deliver by motorbike")
    }
}

class Ship: Transport {
    override fun deliver() {
        println("Deliver by ship")
    }
}

class Boat: Transport {
    override fun deliver() {
        println("Deliver by boat")
    }
}

interface TransportFactory {
    fun makeTransport(type: TransportType): Transport
}

class RoadTransportFactory: TransportFactory {
    override fun makeTransport(type: TransportType): Transport {
        return when(type) {
            TransportType.CAR -> Car()
            TransportType.MOTORBIKE -> MotorBike()
            else -> throw IllegalArgumentException("Cannot find road transport type")
        }
    }
}

class SeaTransportFactory: TransportFactory {
    override fun makeTransport(type: TransportType): Transport {
        return when(type) {
            TransportType.BOAT -> Boat()
            TransportType.SHIP -> Ship()
            else -> throw IllegalArgumentException("Cannot find sea transport type")
        }
    }
}

class LogisticFactory {
    companion object {
        fun createTransport(type: TransportType): Transport = when (type) {
            TransportType.BOAT,
                TransportType.SHIP -> {
                val factory = SeaTransportFactory()
                factory.makeTransport(type)
            }
            TransportType.CAR,
                TransportType.MOTORBIKE -> {
                val factory = RoadTransportFactory()
                factory.makeTransport(type)
            }
        }
    }
}
```

Usage:

```kotlin
    val carDeliver = LogisticFactory.createTransport(TransportType.CAR)
    carDeliver.deliver()
```

Result:
```shell script
Deliver by car
```

Builder
-------

Example:
```kotlin
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
```

Usage:
```kotlin
    val dialog = Dialog.Builder()
        .setTitle("Title")
        .setContent("Hello")
        .setConfirmText("OK")
        .setCancelText("CANCEL")
        .build()
    println(dialog)
```

Result:
```shell script
Dialog(title=Title, content=Hello, confirmText=OK, cancelText=CANCEL)
```

Singleton
---------
[Kotlin object explain](https://www.youtube.com/watch?v=KUk6k865Vgg)

Example:

```kotlin
object Counter {
    private var count = 0

    fun count(): Int {
        return ++count
    }
}
```

Usage:

```kotlin
    println(Counter.count())
    println(Counter.count())
```

Result:

```shell script
1
2
```

Prototype
---------

Example:

```kotlin
data class News(val title: String, val content: String)
```

Usage:

```kotlin
    val firstNews = News("Breaking", "Broken")
    val secondNews = firstNews.copy()

    println(secondNews)
    println("Are first news and second news the same? ${firstNews === secondNews}")
```

Result:

 ```shell script
News(title=Breaking, content=Broken)
Are first news and second news the same? false
```

Structural
==========

Adapter
------

Example:
```kotlin

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
```

Usage:
```kotlin
    val listView = ListView()
    val remoteList = listOf(
        RemoteData("Breaking news", "broken news"),
        RemoteData("Hello", "World")
    )
//    listView.showListViewData(remoteList) // error
    val listViewDataAdapter = ListViewDataAdapter()
    listView.showListViewData(remoteList.map { listViewDataAdapter.toListViewData(it) })
```

Result:
```shell script
ListViewData(title=Breaking news, content=broken news)
ListViewData(title=Hello, content=World)
```

Bridge
------

Example:

```kotlin
interface Device {
    var enabled: Boolean
    var volume: Int
    var channel: Int
    fun printStatus()
}

class Radio : Device {
    override var enabled: Boolean = false
    override var volume: Int = 30
        set(value) {
            field = when {
                value > 100 -> 100
                value < 0 -> 0
                else -> value
            }
        }
    override var channel: Int = 1

    override fun printStatus() {
        println("--------------------------")
        println("| I'm radio.")
        println("| I'm ${if (enabled) "enabled" else "disable"}")
        println("| Current volume is $volume")
        println("| Current channel is $channel")
        println("--------------------------")
    }
}

class Tv: Device {
    override var enabled: Boolean = false
    override var volume: Int = 30
        set(value) {
            field = when {
                value > 100 -> 100
                value < 0 -> 0
                else -> value
            }
        }
    override var channel: Int = 1

    override fun printStatus() {
        println("--------------------------")
        println("| I'm TV.")
        println("| I'm ${if (enabled) "enabled" else "disable"}")
        println("| Current volume is $volume")
        println("| Current channel is $channel")
        println("--------------------------")
    }
}

interface Remote {
    fun power()
    fun volumeDown()
    fun volumeUp()
    fun channelDown()
    fun channelUp()
}

open class BasicRemote(
    private val device: Device
): Remote {

    override fun power() {
        device.enabled = !device.enabled
    }

    override fun volumeDown() {
        device.volume = device.volume - 10
    }

    override fun volumeUp() {
        device.volume = device.volume + 10
    }

    override fun channelDown() {
        device.channel = device.channel - 1
    }

    override fun channelUp() {
        device.channel = device.channel + 1
    }
}

class AdvancedRemote(
    private val device: Device
): BasicRemote(device) {
    fun mute() {
        device.volume = 0
    }
}
```

Usage:

```kotlin
    val device: Device = Tv()
    val advancedRemote = AdvancedRemote(device)
    advancedRemote.mute()
    advancedRemote.channelUp()
    device.printStatus()
```

Result:

```shell script
--------------------------
| I'm TV.
| I'm disable
| Current volume is 0
| Current channel is 2
--------------------------
```

Composite
---------

Example:

```kotlin
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
```

Usage:

```kotlin
    val firstViewGroup = ViewGroup(1, 0,
        listOf(LineView(10, 10), TextView(10, 20, "Hello"))
        )

    val secondViewGroup = ViewGroup(2, 0,
        listOf(firstViewGroup, TextView(30, 30, "World"))
    )

    secondViewGroup.draw()
```

Result:

```shell script
Draw view group to 2, 0
Draw view group to 1, 0
Draw line to 10, 10
Draw text Hello to 10, 20
Draw text World to 30, 30
```

Decorator
---------

Example:

```kotlin
interface DataSource {
    fun writeData(data: String)
    fun readData(): String
}

class ConsoleDataSource: DataSource {
    private var data: String = ""

    override fun writeData(data: String) {
        this.data = data
        println("Writing $data into console")
    }

    override fun readData(): String {
        return data
    }
}

open class DataSourceDecorator(
    private val wrappee: DataSource
) : DataSource {

    override fun writeData(data: String) {
        wrappee.writeData(data)
    }

    override fun readData(): String {
        return wrappee.readData()
    }
}

class EncryptionDecorator(
    private val dataSource: DataSource
): DataSourceDecorator(dataSource) {

    override fun writeData(data: String) {
        dataSource.writeData(encode(data))
    }

    override fun readData(): String {
        return decode(dataSource.readData())
    }

    private fun encode(data: String): String {
        return Base64.getEncoder().encodeToString(data.toByteArray())
    }

    private fun decode(data: String): String {
        return String(Base64.getDecoder().decode(data))
    }
}
```

Usage:

```kotlin
    val plainDataSource = ConsoleDataSource()
    plainDataSource.writeData("Important info")

    val encryptedDataSource = EncryptionDecorator(plainDataSource)
    encryptedDataSource.writeData("Important info")

    println("Got decrypted data: ${encryptedDataSource.readData()}")
```

Result:

```shell script
Writing Important info into console
Writing SW1wb3J0YW50IGluZm8= into console
Got decrypted data: Important info
```

Facade
------

Think about google search, what users see are an input view and a button. Google hides all complexity into their server.

Flyweight
---------

Rather than create an object each time. we can cache the same object and share it by different clients.

Example:

[okio](https://github.com/square/okio) library: ByteStrings and Buffers 

Proxy
-----

Example:

```kotlin
interface ThirdPartyFileStore {
    fun getFile(): String
}

class ThirdPartyFileStoreImpl: ThirdPartyFileStore {
    override fun getFile(): String {
        return Base64.getEncoder().encodeToString("confidential.txt".toByteArray())
    }
}

class ProxyFileStore(
    private val thirdPartyFileStore: ThirdPartyFileStore
): ThirdPartyFileStore {
    override fun getFile(): String {
        return thirdPartyFileStore.getFile()
    }

    fun getFileAndDecode(): String {
        return String(Base64.getDecoder().decode(getFile()))
    }
}
```

Usage:

```kotlin
    val proxy = ProxyFileStore(ThirdPartyFileStoreImpl())

    println("Got file ${proxy.getFile()}")
    println("Got file ${proxy.getFileAndDecode()}")
```

Result:

```shell script
Got file Y29uZmlkZW50aWFsLnR4dA==
Got file confidential.txt
```

Behavioral
==========

Strategy
--------
Example:
```kotlin
fun sumWithCondition(integers: List<Int>, condition: (a: Int) -> Boolean): Int {
    return integers.filter { condition(it) }.sum()
}
```
Usage:
```kotlin
    println("Sum even numbers ${sumWithCondition(list) {it % 2 == 0}}")
    println("Sum odd numbers ${sumWithCondition(list) {it % 2 != 0}}")
    println("Sum all numbers ${sumWithCondition(list) { true }}")
```
Result:
```shell script
Sum even numbers 20
Sum odd numbers 16
Sum all numbers 36
```

Observer
--------

Example:

```kotlin
interface Observable <T> {
    fun register(observer: Observer<T>)
    fun unregister(observer: Observer<T>)
}

interface Observer<T> {
    fun notify(t: T)
}

class WeatherReport: Observable<String> {
    private val observers: HashSet<Observer<String>> = HashSet()

    fun newReport(report: String) {
        for (observer in observers) {
            observer.notify(report)
        }
    }

    override fun register(observer: Observer<String>) {
        observers.add(observer)
    }

    override fun unregister(observer: Observer<String>) {
        observers.remove(observer)
    }
}

class TVStation: Observer<String> {
    override fun notify(t: String) {
        println("TV station got report: $t")
    }

}
class EmailReceiver: Observer<String> {
    override fun notify(t: String) {
        println("Email receiver got report: $t")
    }
}
```

Usage:

```kotlin
    val weatherReport = WeatherReport()
    val emailReceiver = EmailReceiver()
    val tvStation = TVStation()
    weatherReport.register(emailReceiver)
    weatherReport.register(tvStation)

    weatherReport.newReport("Cloudy, Temperature: 23 degrees")

    weatherReport.unregister(emailReceiver)
    weatherReport.unregister(tvStation)
```

Result:

```shell script
TV station got report: Cloudy, Temperature: 23 degrees
Email receiver got report: Cloudy, Temperature: 23 degrees
```

Chain of Responsibility
-----------------------

Example:

```kotlin
abstract class View(private val children: List<View>) {
    fun handleTouchEvent(event: TouchEvent): Boolean {
        if (onEvent(event)) {
            return true
        }
        for (child in children) {
            if (child.handleTouchEvent(event)) {
                return true
            }
        }
        return false
    }

    abstract fun onEvent(event: TouchEvent): Boolean
}

class TextView(private val children: List<View>) : View(children) {
    override fun onEvent(event: TouchEvent): Boolean {
        println("I am TextView, I don't want to handle the event")
        return false
    }
}

class Button(private val children: List<View>): View(children) {
    override fun onEvent(event: TouchEvent): Boolean {
        println("I am Button, Let me handle the event")
        return true
    }
}
```

Usage:

```kotlin
    val chain = TextView(listOf(TextView(listOf(Button(listOf(TextView(listOf())))))))

    chain.handleTouchEvent(TouchEvent())
```

Result:

```shell script
I am TextView, I don't want to handle the event
I am TextView, I don't want to handle the event
I am Button, Let me handle the event
```

Command
-------

Example:

```kotlin
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
```

Usage:

```kotlin
    val editorGui = EditorGui(EditorService())

    editorGui.copyButton.click()
    editorGui.pasteButton.click()
    editorGui.cutButton.click()
```

Result:

```shell script
Copy text
Paste text
Cut text
```

Iterator
--------

Example:

```kotlin
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
```

Usage:

```kotlin
    val myFriends = MyFriends(
        arrayOf(Friend("Tony"), Friend("Tom"), Friend("TT"))
    )

    while (myFriends.hasNext()) {
        println(myFriends.next())
    }
```

Result:

```shell script
Friend(name=Tony)
Friend(name=Tom)
Friend(name=TT)
```

Mediator
--------

Example:
```kotlin
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
```

Usage:

```kotlin
    val dialog = Dialog()
    dialog.checkBox.onCheck(true)
    dialog.radioButton.select(2)
    dialog.button.onClick()
```

Result:

```shell script
myCheckBox is checked true
myRadioButton is selected 2
myButton clicked
```

Memento
-------

Example:

```kotlin
data class Memento(val state: String)

class Originator(var state: String) {
    fun createMemento(): Memento {
        return Memento(state)
    }

    fun restore(memento: Memento) {
        state = memento.state
    }
}

class CareTaker {
    private val mementoList = ArrayList<Memento>()

    fun saveState(state: Memento) {
        mementoList.add(state)
    }

    fun restore(index: Int): Memento {
        return mementoList[index]
    }
}
```

Usage:

```kotlin
    val originator = Originator("initial state")
    val careTaker = CareTaker()
    careTaker.saveState(originator.createMemento())

    originator.state = "State #1"
    originator.state = "State #2"
    careTaker.saveState(originator.createMemento())

    originator.state = "State #3"
    println("Current State: ${originator.state}")

    originator.restore(careTaker.restore(1))
    println("Second State: ${originator.state}")

    originator.restore(careTaker.restore(0))
    println("Third State: ${originator.state}")

    originator.restore(careTaker.restore(1))
    println("Last State: ${originator.state}")
```

Result:

```shell script
Current State: State #3
Second State: State #2
Third State: initial state
Last State: State #2
```

State
-----

Example:

```kotlin
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
```

Usage:

```kotlin
    val ui = UI()

    ui.showData()
    ui.fetch()
    ui.showData()
    ui.done("Done")
    ui.showData()
```

Result:

```
Call fetch to update state
Loading, please be patient
Show: Done
```

Template Method
---------------

Example:

```kotlin
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
```

Usage:

```kotlin
    val videoDownloader = VideoDownloader("https://video")
    videoDownloader.startDownload()
```

Result:

```shell script
Start download https://video
Downloading..... 10%
Downloading..... 20%
Downloading..... 30%
Downloading..... 40%
Downloading..... 50%
Downloading..... 60%
Downloading..... 70%
Downloading..... 80%
Downloading..... 90%
Downloading..... 100%
https://video done
```

Visitor
-------

Example:

```kotlin
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
```

Usage:

```kotlin
    val taxVisitor = TaxVisitor()

    val milk = Necessity(2.5)
    println(milk.accept(taxVisitor))
    val cigars = Tobacco(12.0)
    println(cigars.accept(taxVisitor))
```

Result:

```shell script
2.52
15.84
```
