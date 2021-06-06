# Common info

Project created on base of https://github.com/icerockdev/moko-template.

# Prerequirements

- MacOS for iOS development
- Oracle JDK 11
- Android Studio 4.0+ - https://developer.android.com/studio
- android sdk (setup with android studio)
- xcode 12+ (on macos)
- cocoapods 1.10+ (on macos)
- xcode command line tools (on macos - `xcode-select --install`)

# Build

## Build android

Just open in android studio and press run. Or you can build in CLI - `./gradlew assembleDebug`

## Build ios

1. Install cocoapods dependencies `(cd ios-app && pod install)` (first run to give kotlin compiler native dependencies which we use);
1. Build kotlin shared library framework for iOS cocoapods - `./gradlew :mpp-library:syncMultiPlatformLibraryDebugFrameworkIosX64`;
1. Install cocoapods with already compiled kotlin `(cd ios-app && pod install)` (second run to valid integration of kotlin framework in ios xcode project);
1. Open xcode workspace - `open ios-app/ios-app.xcworkspace` and run app.

After successful run you can change kotlin code and just run app in xcode - all changes of kotlin will be recompiled as needed, or caches will be used. No need `pod install` later or call `sync` gradle task.
