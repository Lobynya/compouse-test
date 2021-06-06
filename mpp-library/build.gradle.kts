/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidLibrary)
    plugin(Deps.Plugins.kotlinMultiplatform)
    plugin(Deps.Plugins.mobileMultiplatform)
    plugin(Deps.Plugins.mokoResources)
    plugin(Deps.Plugins.iosFramework)
    plugin(Deps.Plugins.mokoNetwork)
    plugin(Deps.Plugins.kotlinSerialization)
    plugin(Deps.Plugins.kotlinParcelize)
}

val mppLibs = listOf(
    Deps.Libs.MultiPlatform.multiplatformSettings,
    Deps.Libs.MultiPlatform.napier,
    Deps.Libs.MultiPlatform.mokoParcelize,
    Deps.Libs.MultiPlatform.mokoResources,
    Deps.Libs.MultiPlatform.mokoMvvmCore,
    Deps.Libs.MultiPlatform.mokoMvvmLiveData,
    Deps.Libs.MultiPlatform.mokoMvvmState,
    Deps.Libs.MultiPlatform.mokoUnits,
    Deps.Libs.MultiPlatform.mokoFields,
    Deps.Libs.MultiPlatform.mokoNetwork,
    Deps.Libs.MultiPlatform.mokoErrors,
    Deps.Libs.MultiPlatform.mokoNetworkErrors,
    Deps.Libs.MultiPlatform.mokoCrashReportingCore,
    Deps.Libs.MultiPlatform.mokoCrashReportingCrashlytics,
    Deps.Libs.MultiPlatform.mokoCrashReportingNapier
)
val mppModules = listOf(
    Deps.Modules.Feature.auth
)

dependencies {
    commonMainImplementation(Deps.Libs.MultiPlatform.coroutines)

    commonMainImplementation(Deps.Libs.MultiPlatform.kotlinSerialization)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClient)
    commonMainImplementation(Deps.Libs.MultiPlatform.ktorClientLogging)

    androidMainImplementation(Deps.Libs.Android.lifecycle)

    mppLibs.forEach { commonMainApi(it.common) }
    mppModules.forEach { commonMainApi(project(it.name)) }

    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.mokoTestCore)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.mokoMvvmTest)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.mokoUnitsTest)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.multiplatformSettingsTest)
    commonTestImplementation(Deps.Libs.MultiPlatform.Tests.ktorClientMock)
}

multiplatformResources {
    multiplatformResourcesPackage = "org.example.library"
}

framework {
    mppModules.forEach { export(it) }
    mppLibs.forEach { export(it) }
}

cocoaPods {
    podsProject = file("../ios-app/Pods/Pods.xcodeproj")

    pod("MCRCDynamicProxy", onlyLink = true)
}

mokoNetwork {
    spec("news") {
        inputSpec = file("src/api/openapi.yml")
    }
}
