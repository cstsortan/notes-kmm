//
//  EditNoteModel.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//

import Foundation
import sharedKit

@Observable
class EditNoteModel {
    
    let noteId: Int64
    
    init(noteId: Int64) {
        self.noteId = noteId
        self.vm = DI().getEditNoteViewModel(noteId: noteId)
        Task {
            for await state in vm.uiState {
                self.state = state
            }
        }
        
        Task {
            for await effect in vm.uiEffects {
                self.latestEffect = effect
            }
        }
    }
    
    private let vm: EditNoteViewModel
    
    var state: EditNoteUiState = EditNoteUiState.Loading()
    
    var latestEffect: EditNoteUiEffect? = nil
    
    
    func onAction(action: EditNoteAction) -> Void {
        vm.onAction(action: action)
    }
}
