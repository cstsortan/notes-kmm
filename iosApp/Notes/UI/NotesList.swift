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
    @Binding var selectedNote: Note?
    
    var body: some View {
        List(notes, id: \.id, selection: $selectedNote) { note in
            NoteListItem(note: note, onClick: {
                selectedNote = note
            })
        }
    }
}

struct NotesListPage: View {
    @State private var vm = NotesModel()
    
    var body: some View {
        NavigationView {
            NotesListView(notes: vm.state, selectedNote: $vm.selectedNote)
                .navigationTitle("Notes")
                .toolbar {
                    ToolbarItem(placement: .automatic) {
                        Button {
                            vm.addNoteFormOpen = true
                        } label: {
                            Text("Add Note")
                        }
                    }
                }
                .sheet(isPresented: $vm.addNoteFormOpen) {
                    NavigationView {
                        AddNote(
                            onDismiss: {vm.addNoteFormOpen = false}
                        )
                    }
                }
                .sheet(item: $vm.selectedNote) { note in
                    NavigationView {
                        EditNote(
                            noteId: Int64(note.id),
                            onDismiss: {vm.addNoteFormOpen = false}
                        )
                    }
                }
                
        }
    }
    
}

struct NoteListItem: View {
    let note: Note
    let onClick: () -> Void
    var body: some View {
        Button {
            onClick()
        } label: {
            VStack(alignment: .leading) {
                Text(note.title)
                    .font(.headline)
                Text(note.content)
                    .font(.body)
            }
        }
    }
}

#Preview {
    let notes = [
        Note(id: 1, title: "test1", content: "desc1"),
        Note(id: 2, title: "test2", content: "desc2"),
    ]
    NotesListView(
         notes: notes,
         selectedNote: Binding(get: {nil}, set: {_ in})
         
    )
}
