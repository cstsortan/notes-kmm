package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.models.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn

class NotesViewModel(
    notesRepository: NotesRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> = notesRepository.getNotes().stateIn(
        viewModelScope,
        SharingStarted.Lazily, emptyList()
    )
}