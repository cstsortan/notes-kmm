//
//  NotesViewModel.swift
//  Notes
//
//  Created by Christos Tsortanidis on 27/8/25.
//
import sharedKit
import Foundation

@Observable
class NotesModel {
    private let vm = DI().getNotesViewModel()
    private let repo = DI().getNotesRepository()
    
    var state: [Note] = []
    
    @MainActor
    func startObserver() async {
        for await notes in vm.notes {
            self.state = notes
        }
    }
}
