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
    
    var addNoteFormOpen = false
    var selectedNote: Note? = nil
    
    var state: [Note] = []
    
    init() {
        Task {
            for await notes in vm.notes {
                self.state = notes
            }
        }
    }
}

extension Note: @retroactive Identifiable {}
