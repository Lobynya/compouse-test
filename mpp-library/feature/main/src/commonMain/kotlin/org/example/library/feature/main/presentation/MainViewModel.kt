/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.main.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MainViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
) : ViewModel(), EventsDispatcherOwner<MainViewModel.EventsListener> {

    val hashStorage = HashStorage()

    val workerList: MutableList<Worker>

    init {
        workerList = mutableListOf<Worker>()
        for (i in 0..10000) {
            workerList.add(Worker())
        }
        viewModelScope.launch {
            workerList.map { it.start(hashStorage) }.awaitAll()
        }
    }

    val workerCounters = workerList.map { it.failCounter }
    val successCounters = workerList.map { it.successCounter }

    interface EventsListener
}
