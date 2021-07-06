package org.example.library.feature.main.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.native.concurrent.AtomicReference

actual class HashStorage actual constructor() {
    val _hashSize = MutableStateFlow<Int>(0)

    actual val hashSize: StateFlow<Int> = _hashSize

    private val hash = AtomicReference<List<String>>(listOf())

    actual fun getCurrentHash(): List<String> {
        return hash.value
    }

    actual fun addNewHash(oldHash: List<String>, newHash: List<String>): Boolean {
        return hash.compareAndSet(oldHash, newHash)
    }
}