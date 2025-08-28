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
            Text(note.title)
        }
    }
}

struct NotesListPage: View {
    @State private var vm = NotesModel()
    
    var body: some View {
        NotesListView(notes: vm.state)
    }
    
}

#Preview {
    let notes = [
        Note(id: 1, title: "test1", description: "desc1"),
        Note(id: 2, title: "test2", description: "desc2"),
    ]
    return NotesListView(
         notes: notes
    )
}
