/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("dev.icerock:mobile-multiplatform:0.9.2")
    // should be in sync with Deps.kotlinTestVersion and Deps.kotlinxSerializationPluginVersion
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
    implementation("com.android.tools.build:gradle:4.1.3")
}
