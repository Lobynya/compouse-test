/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidApplication)
    plugin(Deps.Plugins.kotlinAndroid)
    plugin(Deps.Plugins.kotlinKapt)
    plugin(Deps.Plugins.mokoUnits)
    plugin(Deps.Plugins.googleServices) apply false
    plugin(Deps.Plugins.firebaseCrashlytics) apply false
}

android {
    signingConfigs {
        create("release") {
            keyAlias = System.getenv("RELEASE_KEY_ALIAS")
            keyPassword = System.getenv("RELEASE_KEY_PASSWORD")
            storeFile = file("signing/release.jks")
            storePassword = System.getenv("RELEASE_STORE_PASSWORD")
        }
        create("sharedDebug") {
            keyAlias = "debug"
            keyPassword = "debugicerock"
            storeFile = file("signing/debug.jks")
            storePassword = "debugicerock"
        }
    }

    compileSdkVersion(Deps.Android.compileSdk)

    buildFeatures.dataBinding = true

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    defaultConfig {
        minSdkVersion(Deps.Android.minSdk)
        targetSdkVersion(Deps.Android.targetSdk)

        applicationId = "org.example.app"

        versionCode = Integer.parseInt(project.property("VERSION_CODE") as String)
        versionName = project.property("VERSION_NAME") as String

        vectorDrawables.useSupportLibrary = true

        val url = "https://newsapi.org/v2/"
        buildConfigField("String", "BASE_URL", "\"$url\"")
    }

    buildTypes {
        getByName("release") {
            val releaseConfig = signingConfigs.getByName("release")
            signingConfig = when {
                releaseConfig.keyAlias != null -> releaseConfig
                System.getenv("CI") == null -> {
                    logger.warn("used debug signing for release build!")
                    signingConfigs.getByName("sharedDebug")
                }
                else -> {
                    throw IllegalArgumentException("release signing not configured. Set RELEASE_KEY_ALIAS, RELEASE_KEY_PASSWORD, RELEASE_STORE_PASSWORD environment variables.")
                }
            }

            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("sharedDebug")
            isDebuggable = true
            applicationIdSuffix = ".debug"
            ext.set("enableCrashlytics", false)
        }
    }

    flavorDimensions("server")

    productFlavors {
        create("dev") {
            dimension = "server"
            applicationIdSuffix = ".dev"

            val endpoint = "https://newsapi.org/v2/"
            buildConfigField("String", "BASE_URL", "\"$endpoint\"")
        }

        create("stage") {
            dimension = "server"
            applicationIdSuffix = ".stage"

            val endpoint = "https://newsapi.org/v2/"
            buildConfigField("String", "BASE_URL", "\"$endpoint\"")
        }

        create("prod") {
            dimension = "server"

            val endpoint = "https://newsapi.org/v2/"
            buildConfigField("String", "BASE_URL", "\"$endpoint\"")
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(Deps.Libs.Android.appCompat)
    implementation(Deps.Libs.Android.material)
    implementation(Deps.Libs.Android.recyclerView)
    implementation(Deps.Libs.Android.swipeRefreshLayout)
    implementation(Deps.Libs.Android.mokoMvvmDataBinding)

    implementation(platform(Deps.Libs.Android.firebaseBom))
    implementation(Deps.Libs.Android.firebaseCrashlytics)

    implementation(project(":mpp-library"))
}

multiplatformUnits {
    classesPackage = "org.example.app"
    dataBindingPackage = "org.example.app"
    layoutsSourceSet = "main"
}

//apply(plugin = Deps.Plugins.googleServices.id)
//apply(plugin = Deps.Plugins.firebaseCrashlytics.id)