package org.example.library.feature.main.presentation

class HashStorage {
    private val currentHash = mutableListOf<String>()

    fun getCurrentHash(): List<String> = currentHash

    fun addNewHash(newHash: String) {
        currentHash.add(newHash)
    }
}