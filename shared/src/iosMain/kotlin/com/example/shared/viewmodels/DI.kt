package com.example.shared.viewmodels

import com.example.shared.viewmodels.addnote.AddNoteViewModel
import com.example.shared.viewmodels.editnote.EditNoteViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DI: KoinComponent {
    fun getNotesViewModel(): NotesViewModel {
        return get<NotesViewModel>()
    }
    fun getAddNoteViewModel(): AddNoteViewModel {
        return get<AddNoteViewModel>()
    }
    fun getEditNoteViewModel(noteId: Long): EditNoteViewModel {
        return get<EditNoteViewModel> { parametersOf(noteId) }
    }
}