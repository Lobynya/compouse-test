/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    plugin(Deps.Plugins.androidApplication)
    plugin(Deps.Plugins.kotlinAndroid)
}

android {

    packagingOptions {

        exclude("META-INF/ASL2.0")
    }

    compileSdk = 30
    buildToolsVersion ="30.0.3"

    defaultConfig {
        applicationId ="com.lobynya.composetest"
        minSdk =21
        targetSdk =30
        versionCode =1
        versionName ="1.0"

        testInstrumentationRunner ="androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary =true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.0-beta07"
    }
}

dependencies {
    val compose_version = "1.0.0-beta07"
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.0-alpha06")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")

    implementation(project(":mpp-library"))
}

//apply(plugin = Deps.Plugins.googleServices.id)
//apply(plugin = Deps.Plugins.firebaseCrashlytics.id)