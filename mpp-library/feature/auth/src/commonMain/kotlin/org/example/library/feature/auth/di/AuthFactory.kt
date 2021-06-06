/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library.feature.auth.di

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import org.example.library.feature.auth.presentation.AuthViewModel

class AuthFactory {
    fun createAuthViewModel(
        eventsDispatcher: EventsDispatcher<AuthViewModel.EventsListener>
    ) = AuthViewModel(
        eventsDispatcher = eventsDispatcher
    )
}
