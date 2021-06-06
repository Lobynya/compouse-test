/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.detekt) apply false
    id("dev.icerock.gradle.talaiot") version ("2.+")
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
    dependencies {
        plugin(Deps.Plugins.mokoResources)
        plugin(Deps.Plugins.mokoNetwork)
        plugin(Deps.Plugins.mokoUnits)
        plugin(Deps.Plugins.kotlinSerialization)
        plugin(Deps.Plugins.firebaseCrashlytics)
        plugin(Deps.Plugins.googleServices)
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()

        jcenter {
            content {
                includeGroup("com.github.aakira")
            }
        }
    }

    apply(plugin = Deps.Plugins.detekt.id)

    configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        input.setFrom("src/commonMain/kotlin", "src/androidMain/kotlin", "src/iosMain/kotlin")
    }

    dependencies {
        "detektPlugins"(Deps.Libs.Detekt.detektFormatting)
    }

    plugins.withId(Deps.Plugins.androidLibrary.id) {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdkVersion(Deps.Android.compileSdk)

            defaultConfig {
                minSdkVersion(Deps.Android.minSdk)
                targetSdkVersion(Deps.Android.targetSdk)
            }
        }
    }

    configurations.configureEach {
        resolutionStrategy {
            // remove after coroutines native-mt will be merged into stable release
            force(Deps.Libs.MultiPlatform.coroutines)
            // remove after update detekt to 1.17.0+
            force("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
        }
    }
}

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
