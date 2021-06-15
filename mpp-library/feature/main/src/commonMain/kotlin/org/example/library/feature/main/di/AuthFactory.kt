/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.main.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import org.example.library.feature.main.presentation.MainViewModel

class MainFactory {
    fun createMainViewModel(
        eventsDispatcher: EventsDispatcher<MainViewModel.EventsListener>
    ) = MainViewModel(
        eventsDispatcher = eventsDispatcher
    )
}
