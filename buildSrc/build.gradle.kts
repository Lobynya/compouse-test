/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("dev.icerock:mobile-multiplatform:0.9.2")
    // should be in sync with Deps.kotlinTestVersion and Deps.kotlinxSerializationPluginVersion
    implementation("com.android.tools.build:gradle:7.1.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
}
