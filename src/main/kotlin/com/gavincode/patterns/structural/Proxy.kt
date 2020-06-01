package com.gavincode.patterns.structural

import java.util.*

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

fun main() {
    val proxy = ProxyFileStore(ThirdPartyFileStoreImpl())

    println("Got file ${proxy.getFile()}")
    println("Got file ${proxy.getFileAndDecode()}")
}