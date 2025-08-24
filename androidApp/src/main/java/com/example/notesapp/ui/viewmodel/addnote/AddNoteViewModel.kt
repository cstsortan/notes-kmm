package com.example.notesapp.ui.viewmodel.addnote

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
    private val _uiState = MutableStateFlow<AddNoteUiState>(AddNoteUiState.Content())
    val uiState: StateFlow<AddNoteUiState> = _uiState.asStateFlow()

    fun onAction(action: AddNoteAction) {
        when (action) {
            is AddNoteAction.UpdateTitle -> updateTitle(action.title)
            is AddNoteAction.UpdateContent -> updateContent(action.content)
            is AddNoteAction.ClearFields -> clearFields()
            is AddNoteAction.SaveNote -> saveNote()
        }
    }

    private fun updateTitle(title: String) {
        val currentState = _uiState.value
        if (currentState is AddNoteUiState.Content) {
            _uiState.value = currentState.copy(noteTitle = title)
        }
    }

    private fun updateContent(content: String) {
        val currentState = _uiState.value
        if (currentState is AddNoteUiState.Content) {
            _uiState.value = currentState.copy(noteContent = content)
        }
    }

    private fun clearFields() {
        val currentState = _uiState.value
        if (currentState is AddNoteUiState.Content) {
            _uiState.value = currentState.copy(noteTitle = "", noteContent = "")
        }
    }

    private fun saveNote() {
        val currentState = _uiState.value
        if (currentState is AddNoteUiState.Content) {
            _uiState.value = AddNoteUiState.Loading
            
            viewModelScope.launch {
                try {
                    notesRepository.addNote(CreateNote(currentState.noteTitle, currentState.noteContent))
                    _uiState.value = AddNoteUiState.Content()
                } catch (e: Exception) {
                    _uiState.value = AddNoteUiState.Error(e.message ?: "Unknown error occurred")
                }
            }
        }
    }
}