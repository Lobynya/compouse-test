package org.example.library.feature.main.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.atomic.AtomicReference

actual class HashStorage actual constructor() {

    private val _hashSize = MutableStateFlow<Int>(0)

    actual val hashSize: StateFlow<Int> = _hashSize

    private val hash = AtomicReference<List<String>>(listOf())

    actual fun getCurrentHash(): List<String> {
        return hash.get()
    }

    actual fun addNewHash(oldHash: List<String>, newHash: List<String>): Boolean {
        if( hash.compareAndSet(oldHash, newHash)){
            _hashSize.value = newHash.size
            return true
        }
        return false
    }
}