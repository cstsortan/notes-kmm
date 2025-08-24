//
//  ContentView.swift
//  iosApp
//
//  Created by Christos Tsortanidis on 24/8/25.
//

import SwiftUI
import sharedKit

struct ContentView: View {
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text(Platform_iosKt.platform())
        }
        .padding()
        .onAppear {
            
        }
    }
}

#Preview {
    ContentView()
}
