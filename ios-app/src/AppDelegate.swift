/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import SwiftUI
import MultiPlatformLibrary

@main
struct LandmarksApp: App {
    
    init() {
            AppComponent.factory = SharedFactory(
                      settings: AppleSettings(delegate: UserDefaults.standard),
                      antilog: DebugAntilog(defaultTag: "MPP")
                  )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView(AppComponent.factory.mainFactory.createMainViewModel(eventsDispatcher: <#T##EventsDispatcher<MainViewModelEventsListener>#>))
        }
    }
}
