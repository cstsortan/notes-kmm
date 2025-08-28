//
//  NotesList.swift
//  Notes
//
//  Created by Christos Tsortanidis on 28/8/25.
//

import SwiftUI
import sharedKit

struct NotesListView: View {
    var notes: [Note] = []
    
    var body: some View {
        List(notes, id: \.id) { note in
            NoteListItem(note: note)
        }
    }
}

struct NotesListPage: View {
    @State private var vm = NotesModel()
    @State private var addNoteFormOpen = false
    
    var body: some View {
        NavigationView {
            NotesListView(notes: vm.state)
                .navigationTitle("Notes")
                .toolbar {
                    ToolbarItem(placement: .automatic) {
                        Button {
                            addNoteFormOpen = true
                        } label: {
                            Text("Add Note")
                        }
                    }
                }
                .sheet(isPresented: $addNoteFormOpen) {
                    NavigationView {
                        AddNote(
                            onDismiss: {addNoteFormOpen = false}
                        )
                    }
                }
        }
    }
    
}

struct NoteListItem: View {
    let note: Note
    var body: some View {
        VStack(alignment: .leading) {
            Text(note.title)
                .font(.headline)
            Text(note.content)
                .font(.body)
        }
    }
}

#Preview {
    let notes = [
        Note(id: 1, title: "test1", content: "desc1"),
        Note(id: 2, title: "test2", content: "desc2"),
    ]
    return NotesListView(
         notes: notes
    )
}
