package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shared.repository.NotesRepository
import com.example.shared.models.CreateNote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNoteViewModel(
    val notesRepository: NotesRepository
): ViewModel() {
    private val _noteTitle = MutableStateFlow("")
    private val _noteContent = MutableStateFlow("")

    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()
    val noteDescription: StateFlow<String> = _noteContent.asStateFlow()

    fun setNoteTitle(title: String) {
        _noteTitle.value = title
    }

    fun setNoteDescription(content: String) {
        _noteContent.value = content
    }

    fun clearFields() {
        _noteTitle.value = ""
        _noteContent.value = ""
    }

    fun saveNote() {
        // Logic to save the note
        // This could involve calling a repository or database
        viewModelScope.launch {
            notesRepository.addNote(CreateNote(_noteTitle.value, _noteContent.value))
        }
    }
}