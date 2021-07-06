package org.example.library.feature.main.presentation

import com.github.aakira.napier.Napier
import com.soywiz.klock.DateTime
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class Worker() {

    private var scope =
        CoroutineScope(Dispatchers.Default) // creating the scope to run the coroutine. It consists of Dispatchers.Main (coroutine will run in the Main context) and job to handle the cancellation of the coroutine.

    private val _failCounter = MutableStateFlow(0)
    val failCounter: StateFlow<Int> = _failCounter
    private val _successCounter = MutableStateFlow(0)
    val successCounter: StateFlow<Int> = _successCounter
    private val _lastTime = MutableStateFlow(0L)
    val lastTime: StateFlow<Long> = _lastTime

    fun start(targetStorage: HashStorage) =
        scope.async {
            repeat(REPEAT_COUNT) {
                tryHashCalculate(targetStorage)
            }
        }

    private suspend fun tryHashCalculate(targetStorage: HashStorage) {
        val startTime = DateTime.now()
        val currentHash = targetStorage.getCurrentHash()
        var newHash: String =
            ("${this.hashCode()}\n" + currentHash.toString()).hashCode().toString()
        repeat(100000){
            newHash = (newHash + 100).hashCode().toString()
        }
        if (targetStorage.addNewHash(currentHash, currentHash.plus(newHash))) {
            _successCounter.value = _successCounter.value + 1
        } else {
            _failCounter.value = _failCounter.value + 1
        }
        val endTime = DateTime.now()
        _lastTime.value = endTime.unixMillisLong - startTime.unixMillisLong
    }

    companion object {
        const val REPEAT_COUNT = 10000
    }
}