package org.example.library.feature.main

import dev.icerock.moko.test.runBlocking
import org.example.library.feature.main.presentation.HashStorage
import org.example.library.feature.main.presentation.Worker
import kotlin.test.Test
import kotlin.test.assertEquals

class MainTest {

    @Test
    fun testMultiTheards() {
        val hashStorage = HashStorage()
        val workerList = mutableListOf<Worker>()
        runBlocking {
            for (i in 0..1000) {
                workerList.add(Worker(coroutineScope = this))
            }
            workerList.forEach { it.start(hashStorage) }
        }
        val failCount = workerList.sumBy { it.failCounter.value }
        assertEquals(
            expected = 0,
            actual = failCount
        )
    }
}