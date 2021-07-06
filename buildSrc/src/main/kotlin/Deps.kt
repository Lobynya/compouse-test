/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */


object Deps {
    private const val materialVersion = "1.2.1"
    private const val recyclerViewVersion = "1.1.0"
    private const val swipeRefreshLayoutVersion = "1.1.0"
    private const val constraintLayoutVersion = "2.0.0"
    private const val lifecycleVersion = "2.2.0"
    private const val glideVersion = "4.12.0"
    private const val androidAppCompatVersion = "1.1.0"
    private const val espressoCoreVersion = "3.2.0"
    private const val testRunnerVersion = "1.2.0"
    private const val testExtJunitVersion = "1.1.1"
    private const val androidFiftyShades = "-SNAPSHOT"
    private const val cameraxVersion = "1.0.0-beta12"
    private const val cameraViewVersion = "1.0.0-alpha19"
    private const val zxingCoreVersion = "3.2.0"
    private const val zxingVersion = "3.0.2@aar"
    private const val segmentedProgressBarVersion = "0.0.1"
    private const val inputMaskVersion = "6.0.0"
    private const val swipeRevealLayoutVersion = "1.4.1"
    private const val photoViewVersion = "2.3.0"

    private const val googleServicesVersion = "4.3.4"
    private const val firebaseCrashlyticsVersion = "2.3.0"
    private const val firebaseBomVersion = "26.0.0"

    // should be as kotlin version! see buildSrc/build.gradle.kts
    private const val kotlinTestVersion = "1.4.32"
    // should be as kotlin version! see buildSrc/build.gradle.kts
    private const val kotlinxSerializationPluginVersion = "1.4.32"
    private const val kotlinxSerializationVersion = "1.1.0"
    private const val kotlinxDateTimeVersion = "0.1.1"
    private const val coroutinesVersion = "1.4.3-native-mt"

    private const val detektVersion = "1.15.0"

    private const val mokoGraphicsVersion = "0.6.1"
    private const val mokoParcelizeVersion = "0.6.1"
    private const val mokoResourcesVersion = "0.15.1"
    private const val mokoMvvmVersion = "0.10.1"
    private const val mokoErrorsVersion = "0.3.3"
    private const val mokoUnitsVersion = "0.5.0"
    private const val mokoPermissionsVersion = "0.9.0"
    private const val mokoMediaVersion = "0.8.2"
    private const val mokoFieldsVersion = "0.7.1"
    private const val mokoTestVersion = "0.3.0"
    private const val mokoCrashReportingVersion = "0.1.2"

    private const val multiplatformSettingsVersion = "0.7.4"
    private const val napierVersion = "1.4.1"

    private const val androidCoreTestingVersion = "2.1.0"
    private const val robolectricVersion = "4.4"
    private const val klockVersion = "1.12.0"

    object Android {
        const val compileSdk = 30
        const val targetSdk = 30
        const val minSdk = 21
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val kotlinParcelize = GradlePlugin(id = "kotlin-parcelize")
        val kotlinSerialization = GradlePlugin(
            id = "org.jetbrains.kotlin.plugin.serialization",
            module = "org.jetbrains.kotlin:kotlin-serialization:$kotlinxSerializationPluginVersion"
        )

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")


        val mokoResources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:$mokoResourcesVersion"
        )
        val mokoUnits = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-units",
            module = "dev.icerock.moko:units-generator:$mokoUnitsVersion"
        )

        val googleServices = GradlePlugin(
            id = "com.google.gms.google-services",
            module = "com.google.gms:google-services:$googleServicesVersion"
        )
        val firebaseCrashlytics = GradlePlugin(
            id = "com.google.firebase.crashlytics",
            module = "com.google.firebase:firebase-crashlytics-gradle:$firebaseCrashlyticsVersion"
        )

        val detekt = GradlePlugin(
            id = "io.gitlab.arturbosch.detekt",
            version = detektVersion
        )
    }

    object Libs {
        object Android {
            const val appCompat =
                "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material =
                "com.google.android.material:material:$materialVersion"
            const val recyclerView =
                "androidx.recyclerview:recyclerview:$recyclerViewVersion"
            const val swipeRefreshLayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion"
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
            const val glide =
                "com.github.bumptech.glide:glide:$glideVersion"
            const val lifecycle =
                "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val firebaseBom =
                "com.google.firebase:firebase-bom:$firebaseBomVersion"
            const val firebaseCrashlytics =
                "com.google.firebase:firebase-crashlytics-ktx"
            const val firebaseAnalytics =
                "com.google.firebase:firebase-analytics-ktx"
            const val mokoMvvmDataBinding = 
                "dev.icerock.moko:mvvm-databinding:$mokoMvvmVersion"
            /** Цветные и кастомизируемые тени - https://github.com/Miha-x64/FiftyShades */
            const val fiftyShades =
                "com.github.Miha-x64:FiftyShades:$androidFiftyShades"
            /** Работа с CameraX
                Полезные ссылки для ознакомления - https://github.com/android/camera-samples, https://github.com/android/camera-samples,
                https://codelabs.developers.google.com/codelabs/camerax-getting-started#0
             */
            const val cameraCore = "androidx.camera:camera-core:$cameraxVersion"
            const val cameraCamera2 = "androidx.camera:camera-camera2:$cameraxVersion"
            const val cameraLifecycle = "androidx.camera:camera-lifecycle:$cameraxVersion"
            const val cameraPreview = "androidx.camera:camera-view:$cameraViewVersion"
            /** Сканирование штрихкодов, qr-кодов с помощью камеры - https://github.com/journeyapps/zxing-android-embedded */
            const val zxingCore =
                "com.google.zxing:core:$zxingCoreVersion"
            const val zxing =
                "com.journeyapps:zxing-android-embedded:$zxingVersion"
            /** Прогрессбар по сегментам, как в инстаграм - https://github.com/TOrnelas/SegmentedProgressBar */
            const val segmentedProgressBar = "com.github.TOrnelas:SegmentedProgressBar:$segmentedProgressBarVersion"
            /** must have библиотека, если нужно использовать какую то форму для ввода, много уже предусмотренных паттернов
                таких, как телефон, даты, форматы кредитки, ip и т.д.
                Гайд по библиотеке - https://github.com/RedMadRobot/input-mask-android
             */
            const val inputMask =
                "com.redmadrobot:input-mask-android:$inputMaskVersion"
            
            /** easy-to-use библиотека, позволяет добавить swipe-actions для элементов списка
                (https://github.com/chthai64/SwipeRevealLayout)
             */
            const val swipeRevealLayout =
                "com.chauthai.swipereveallayout:swipe-reveal-layout:$swipeRevealLayoutVersion"
            /**
                Библиотека с вьюхой, которая из коробки имеет scroll/scale/double tap actions для картинки.
                Что примечательно - хорошо работает с glide
                Ссылка на библиотеку: https://github.com/Baseflow/PhotoView
            */
            const val photoView =
                "com.github.chrisbanes:PhotoView:$photoViewVersion"
            
            object Tests {
                const val espressoCore =
                    "androidx.test.espresso:espresso-core:$espressoCoreVersion"
                const val kotlinTestJUnit =
                    "org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestVersion"
                const val testCore =
                    "androidx.test:core:1.3.0"
                const val robolectric =
                    "org.robolectric:robolectric:4.3"
                const val testRunner =
                    "androidx.test:runner:$testRunnerVersion"
                const val testRules =
                    "androidx.test:rules:$testRunnerVersion"
                const val testExtJunit =
                    "androidx.test.ext:junit:$testExtJunitVersion"
            }
        }

        object Tests {
            const val kotlinTestJUnit =
                "org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestVersion"
            const val androidCoreTesting =
                "androidx.arch.core:core-testing:$androidCoreTestingVersion"
            const val coroutinesTest =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"
            const val mokoTest =
                "dev.icerock.moko:test-core:$mokoTestVersion"
            const val mokoMvvmTest =
                "dev.icerock.moko:mvvm-test:$mokoMvvmVersion"
            const val mokoPermissionsTest =
                "dev.icerock.moko:permissions-test:$mokoPermissionsVersion"
            const val mokoMediaTest =
                "dev.icerock.moko:media-test:$mokoMediaVersion"
            const val mokoUnitsTest =
                "dev.icerock.moko:units-test:$mokoUnitsVersion"
            const val robolectric =
                "org.robolectric:robolectric:$robolectricVersion"
            const val multiplatformSettingsTest =
                "com.russhwolf:multiplatform-settings-test:$multiplatformSettingsVersion"
        }

        object MultiPlatform {
            const val kotlinSerialization =
                "org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion"
            const val kotlinxDateTime =
                "org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTimeVersion"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"

            val mokoResources = "dev.icerock.moko:resources:$mokoResourcesVersion"
                .defaultMPL(ios = true)
            val mokoParcelize = "dev.icerock.moko:parcelize:$mokoParcelizeVersion"
                .defaultMPL(ios = true)
            val mokoGraphics = "dev.icerock.moko:graphics:$mokoGraphicsVersion"
                .defaultMPL(ios = true)
            val mokoMvvmCore = "dev.icerock.moko:mvvm-core:$mokoMvvmVersion"
                .defaultMPL(ios = true)
            val mokoMvvmLiveData = "dev.icerock.moko:mvvm-livedata:$mokoMvvmVersion"
                .defaultMPL(ios = true)
            val mokoMvvmState = "dev.icerock.moko:mvvm-state:$mokoMvvmVersion"
                .defaultMPL(ios = true)
            val mokoErrors = "dev.icerock.moko:errors:$mokoErrorsVersion"
                .defaultMPL(ios = true)
            val mokoPermissions = "dev.icerock.moko:permissions:$mokoPermissionsVersion"
                .defaultMPL(ios = true)
            val mokoMedia = "dev.icerock.moko:media:$mokoMediaVersion"
                .defaultMPL(ios = true)
            val mokoUnits = "dev.icerock.moko:units:$mokoUnitsVersion"
                .defaultMPL(ios = true)
            val mokoFields = "dev.icerock.moko:fields:$mokoFieldsVersion"
                .defaultMPL(ios = true)
            val mokoCrashReportingCore = "dev.icerock.moko:crash-reporting-core:$mokoCrashReportingVersion"
                .defaultMPL(ios = true)
            val mokoCrashReportingCrashlytics = "dev.icerock.moko:crash-reporting-crashlytics:$mokoCrashReportingVersion"
                .defaultMPL(ios = true)
            val mokoCrashReportingNapier = "dev.icerock.moko:crash-reporting-napier:$mokoCrashReportingVersion"
                .defaultMPL(ios = true)

            val multiplatformSettings =
                "com.russhwolf:multiplatform-settings:$multiplatformSettingsVersion"
                .defaultMPL(ios = true)
            val napier = "com.github.aakira:napier:$napierVersion".let {
                MultiPlatformLibrary(
                    common = it,
                    iosX64 = it.replace(Regex("(.*):(.*):(.*)"), "$1:$2-iosX64:$3"),
                    iosArm64 = it.replace(Regex("(.*):(.*):(.*)"), "$1:$2-iosArm64:$3")
                )
            }
            val klock = MultiPlatformLibrary(
                common = "com.soywiz.korlibs.klock:klock:$klockVersion"
            )
            object Tests {
                const val kotlinTest =
                    "org.jetbrains.kotlin:kotlin-test-common:$kotlinTestVersion"
                const val kotlinTestAnnotations =
                    "org.jetbrains.kotlin:kotlin-test-annotations-common:$kotlinTestVersion"
                const val mokoMvvmTest = "dev.icerock.moko:mvvm-test:$mokoMvvmVersion"
                const val mokoTestCore = "dev.icerock.moko:test-core:$mokoTestVersion"
                const val mokoUnitsTest = "dev.icerock.moko:units-test:$mokoUnitsVersion"
                const val multiplatformSettingsTest =
                    "com.russhwolf:multiplatform-settings-test:$multiplatformSettingsVersion"
            }
        }

        object Detekt {
            const val detektFormatting =
                "io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion"
        }
    }

    object Modules {
        object Feature {
            val main = MultiPlatformModule(
                name = ":mpp-library:feature:main",
                exported = true
            )
        }
    }
}
