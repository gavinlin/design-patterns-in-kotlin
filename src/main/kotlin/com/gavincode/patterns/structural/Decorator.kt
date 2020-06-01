package com.gavincode.patterns.structural

import java.util.*

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

fun main() {
    val plainDataSource = ConsoleDataSource()
    plainDataSource.writeData("Important info")

    val encryptedDataSource = EncryptionDecorator(plainDataSource)
    encryptedDataSource.writeData("Important info")

    println("Got decrypted data: ${encryptedDataSource.readData()}")
}
