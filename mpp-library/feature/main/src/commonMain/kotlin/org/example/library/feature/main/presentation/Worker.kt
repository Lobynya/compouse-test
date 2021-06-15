package org.example.library.feature.main.presentation

import com.github.aakira.napier.Napier
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class Worker() {

    private var job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.Default) // creating the scope to run the coroutine. It consists of Dispatchers.Main (coroutine will run in the Main context) and job to handle the cancellation of the coroutine.

    private val _failCounter = MutableStateFlow(0)
    val failCounter: StateFlow<Int> = _failCounter
    private val _successCounter = MutableStateFlow(0)
    val successCounter: StateFlow<Int> = _successCounter

    fun start(targetStorage: HashStorage) =
        scope.async {
            repeat(1000) {
                tryHashCalculate(targetStorage)
            }
        }

    private suspend fun tryHashCalculate(targetStorage: HashStorage) {
        try {
            Napier.i("try", tag = "worker ${hashCode()}")
            val newHash = ("${this.hashCode()}\n" + targetStorage.getCurrentHash()).hashCode()
            targetStorage.addNewHash(newHash.toString())
            _successCounter.value = _successCounter.value + 1
        } catch (exception: Exception) {
            _failCounter.value = _failCounter.value + 1
            Napier.i("add new hash fail", exception, "worker")
        }
    }
}