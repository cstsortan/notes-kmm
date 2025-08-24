package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shared.repository.NotesRepository
import com.example.shared.models.Note
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(
    notesRepository: NotesRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = notesRepository.getNotes().stateIn(
        viewModelScope,
        SharingStarted.Lazily, emptyList()
    )
}