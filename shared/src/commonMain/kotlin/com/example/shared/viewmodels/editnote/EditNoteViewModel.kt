package com.example.shared.viewmodels.editnote

import com.example.shared.models.CreateNote
import com.example.shared.repository.NotesRepository
import com.example.shared.viewmodels.base.ViewModelBase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditNoteViewModel(
    val noteId: Int,
    val notesRepository: NotesRepository
) : ViewModelBase() {

    private val _uiState = MutableStateFlow<EditNoteUiState>(EditNoteUiState.Loading)
    val uiState: StateFlow<EditNoteUiState> = _uiState.asStateFlow()
    
    private val _uiEffects = MutableSharedFlow<EditNoteUiEffect>()
    val uiEffects: SharedFlow<EditNoteUiEffect> = _uiEffects.asSharedFlow()

    fun onAction(action: EditNoteAction) {
        when (action) {
            is EditNoteAction.UpdateTitle -> updateTitle(action.title)
            is EditNoteAction.UpdateContent -> updateContent(action.content)
            is EditNoteAction.ClearFields -> clearFields()
            is EditNoteAction.SaveNote -> saveNote()
            is EditNoteAction.Initialize -> initialize()
        }
    }

    private fun initialize() {
        scope.launch {
            try {
                val note = notesRepository.getNote(noteId).first()
                if (note != null) {
                    _uiState.value = EditNoteUiState.Content(
                        noteTitle = note.title,
                        noteContent = note.content
                    )
                } else {
                    _uiState.value = EditNoteUiState.Error("Note not found")
                    _uiEffects.emit(EditNoteUiEffect.ShowError("Note not found"))
                }
            } catch (e: Exception) {
                _uiState.value = EditNoteUiState.Error(e.message ?: "Unknown error occurred")
                _uiEffects.emit(EditNoteUiEffect.ShowError(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun updateTitle(title: String) {
        val currentState = _uiState.value
        if (currentState is EditNoteUiState.Content) {
            _uiState.value = currentState.copy(noteTitle = title)
        }
    }

    private fun updateContent(content: String) {
        val currentState = _uiState.value
        if (currentState is EditNoteUiState.Content) {
            _uiState.value = currentState.copy(noteContent = content)
        }
    }

    private fun clearFields() {
        val currentState = _uiState.value
        if (currentState is EditNoteUiState.Content) {
            _uiState.value = currentState.copy(noteTitle = "", noteContent = "")
        }
    }

    private fun saveNote() {
        val currentState = _uiState.value
        if (currentState is EditNoteUiState.Content) {
            _uiState.value = EditNoteUiState.Loading
            
            scope.launch {
                try {
                    notesRepository.updateNote(
                        id = noteId,
                        CreateNote(currentState.noteTitle, currentState.noteContent)
                    )
                    _uiState.value = EditNoteUiState.Content(
                        noteTitle = currentState.noteTitle,
                        noteContent = currentState.noteContent
                    )
                    _uiEffects.emit(EditNoteUiEffect.NavigateBack)
                } catch (e: Exception) {
                    _uiState.value = EditNoteUiState.Error(e.message ?: "Unknown error occurred")
                    _uiEffects.emit(EditNoteUiEffect.ShowError(e.message ?: "Unknown error occurred"))
                }
            }
        }
    }
}