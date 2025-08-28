//
//  AddNote.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//
import SwiftUI
import sharedKit

struct AddNote: View {
    @State private var model = AddNoteModel()
    let onDismiss: () -> Void
    var body: some View {
        AddNoteView(state: model.state, onAction: model.onAction)
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

struct AddNoteView: View {
    
    let state: AddNoteUiState
    let onAction: (_ action: AddNoteAction) -> Void
    
    var body: some View {
        Group {
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
}

struct AddNoteForm: View {
    @Binding var title: String
    @Binding var content: String
    let onSubmit: () -> Void
    let onCancel: () -> Void
    
    var body: some View {
        Form {
            TextField("Title", text: $title)
            TextField("Content", text: $content)
        }
        .toolbar {
            ToolbarItem(placement: .primaryAction) {
                Button {
                    onSubmit()
                } label: {
                    Text("Save")
                }
            }
            ToolbarItem(placement: .cancellationAction) {
                Button(role: .cancel) {
                    onCancel()
                } label: {
                    Text("Cancel")
                }
            }
        }
    }
}

private struct AddNoteFormPreview: View {
    @State private var title = ""
    @State private var content = ""
    var body: some View {
        AddNoteForm(title: $title, content: $content) {
            
        } onCancel: {
            
        }
    }
}

#Preview {
    AddNoteFormPreview()
}
