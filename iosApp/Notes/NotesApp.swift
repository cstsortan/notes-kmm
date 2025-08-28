//
//  NotesApp.swift
//  iosApp
//
//  Created by Christos Tsortanidis on 24/8/25.
//

import SwiftUI
import sharedKit

@main
struct NotesApp: App {
    init() {
        startKoinIos()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
