//
//  EditNote.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//
import SwiftUI
import sharedKit

struct EditNote: View {
    
    let noteId: Int64
    
    init(noteId: Int64, onDismiss: @escaping () -> Void) {
        self.noteId = noteId
        self.onDismiss = onDismiss
        self.model = EditNoteModel(noteId: noteId)
    }
    
    @State private var model: EditNoteModel
    
    let onDismiss: () -> Void
    var body: some View {
        EditNoteView(state: model.state, onAction: model.onAction)
            .task(id: model.latestEffect) {
                switch onEnum(of: model.latestEffect) {
                    case nil : break
                case .navigateBack:
                    onDismiss()
                case .showError(let message):
                    print(message)
                }
                    
            }
    }
}

struct EditNoteView: View {
    
    let state: EditNoteUiState
    let onAction: (_ action: EditNoteAction) -> Void
    
    var body: some View {
        switch onEnum(of: state) {
        case .loading:
            ProgressView()
        case .content(let state):
            AddNoteForm(title: Binding(
                get: {state.noteTitle},
                set: {onAction(.UpdateTitle(title: $0))}
            ), content: Binding(
                get: {state.noteContent},
                set: {onAction(.UpdateContent(content: $0))}
            ), onSubmit: {
                onAction(.SaveNote())
            }, onCancel: {
                onAction(.Cancel())
            })
        case .error(let state):
            Text(state.message)
        }
    }
}

