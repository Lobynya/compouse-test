/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.library

import com.github.aakira.napier.Antilog
import com.github.aakira.napier.Napier
import com.russhwolf.settings.Settings
import dev.icerock.moko.crashreporting.crashlytics.CrashlyticsLogger
import dev.icerock.moko.crashreporting.napier.CrashReportingAntilog
import org.example.library.feature.main.di.MainFactory

class SharedFactory(
    settings: Settings,
    antilog: Antilog
) {

    private val keyValueStorage: KeyValueStorage by lazy { KeyValueStorage(settings) }



    // init factories here
    val mainFactory: MainFactory by lazy {
        MainFactory()
    }

    init {
        Napier.base(antilog)
    }
}
