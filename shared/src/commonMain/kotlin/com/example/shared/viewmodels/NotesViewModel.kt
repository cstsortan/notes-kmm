package com.example.shared.viewmodels

import com.example.shared.models.Note
import com.example.shared.repository.NotesRepository
import com.example.shared.viewmodels.base.ViewModelBase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(
    notesRepository: NotesRepository
) : ViewModelBase() {

    val notes: StateFlow<List<Note>> = notesRepository.getNotes().stateIn(
        scope,
        SharingStarted.Lazily, emptyList()
    )
}