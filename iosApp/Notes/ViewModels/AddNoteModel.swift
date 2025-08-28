//
//  AddNoteModel.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//
import Foundation
import sharedKit

@Observable
class AddNoteModel {
    private let vm = DI().getAddNoteViewModel()
    
    var state: AddNoteUiState = AddNoteUiState.Loading()
    
    var latestEffect: AddNoteUiEffect? = nil
    
    init() {
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
    
    func onAction(action: AddNoteAction) -> Void {
        vm.onAction(action: action)
    }
}
