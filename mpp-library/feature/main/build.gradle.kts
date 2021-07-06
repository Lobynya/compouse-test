/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
}

android{
    configurations{
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

kotlin {
    macosX64("native") { // on macOS
        binaries {
            executable()
        }
    }
}

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    commonMainImplementation(Deps.Libs.MultiPlatform.mokoMvvmLiveData.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.mokoResources.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.klock.common)
    commonMainImplementation(Deps.Libs.MultiPlatform.napier.common)

}
