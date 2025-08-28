//
//  NoteForm.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//
import SwiftUI

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
