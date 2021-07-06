/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.main.presentation

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MediatorLiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.mergeWith
import dev.icerock.moko.mvvm.livedata.readOnly
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MainViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
) : ViewModel(), EventsDispatcherOwner<MainViewModel.EventsListener> {

    private val hashStorage = HashStorage()
    private val _isButtonEnabled = MutableStateFlow<Boolean>(true)
    val isButtonEnabled: StateFlow<Boolean> = _isButtonEnabled

    private val _workerList: MutableList<Worker> = mutableListOf<Worker>()
    val workerList: List<Worker> get() = _workerList

    private val _successCounters = MutableStateFlow<Int>(0)
    private val _failCounter = MutableStateFlow<Int>(0)
    private val _workTime = MutableStateFlow<Long>(0L)
    val successCounters = _successCounters.map { it.toString() }
    val failCounter = _failCounter.map { it.toString() }
    val workTime = _workTime.map { "$it ms" }
    val successPercentage: Flow<String> =
        _successCounters.zip(_failCounter) { success, fail ->
            if (success + fail != 0)
                "${success.toFloat() * 100 / (success + fail)}%"
            else "0%"
        }
    val fatalError = MutableStateFlow("")
    var lastHashSize = -1

    init {
        for (i in 0..8) {
            _workerList.add(Worker())
        }
        viewModelScope.launch {
            hashStorage.hashSize.collect {
                if(lastHashSize >= it){
                    fatalError.value = "oh no $it"
                }
                lastHashSize = it
            }
        }
    }

    fun onStartTap() {
        _isButtonEnabled.value = false

        workerList.forEach {
            viewModelScope.launch {
                it.failCounter.collect { _failCounter.value = calcTotalFail() }
            }
            viewModelScope.launch {
                it.successCounter.collect { _successCounters.value = calcTotalSuccess() }
            }
            viewModelScope.launch {
                it.lastTime.collect { _workTime.value = calcTotalTime() }
            }
        }

        viewModelScope.launch {
            workerList.map { it.start(hashStorage) }.awaitAll()
            _isButtonEnabled.value = true
        }
    }


    private fun calcTotalSuccess() = workerList.sumBy { it.successCounter.value }
    private fun calcTotalFail() = workerList.sumBy { it.failCounter.value }
    private fun calcTotalTime() = workerList.sumOf { it.lastTime.value } / workerList.size

    interface EventsListener
}
