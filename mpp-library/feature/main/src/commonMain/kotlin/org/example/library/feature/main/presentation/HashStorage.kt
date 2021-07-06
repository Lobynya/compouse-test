package org.example.library.feature.main.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.internal.AtomicDesc

expect class HashStorage() {
    val hashSize: StateFlow<Int>

    fun getCurrentHash(): List<String>

    fun addNewHash(oldHash: List<String>, newHash: List<String>): Boolean
}