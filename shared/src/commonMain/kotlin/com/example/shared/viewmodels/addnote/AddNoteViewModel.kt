package com.example.shared.viewmodels.addnote

import com.example.shared.models.CreateNote
import com.example.shared.repository.NotesRepository
import com.example.shared.viewmodels.base.ViewModelBase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNoteViewModel(
    val notesRepository: NotesRepository
): ViewModelBase() {
    private val _uiState = MutableStateFlow<AddNoteUiState>(AddNoteUiState.Content())
    val uiState: StateFlow<AddNoteUiState> = _uiState.asStateFlow()
    
    private val _uiEffects = MutableSharedFlow<AddNoteUiEffect>()
    val uiEffects: SharedFlow<AddNoteUiEffect> = _uiEffects.asSharedFlow()

    fun onAction(action: AddNoteAction) {
        when (action) {
            is AddNoteAction.UpdateTitle -> updateTitle(action.title)
            is AddNoteAction.UpdateContent -> updateContent(action.content)
            is AddNoteAction.ClearFields -> clearFields()
            is AddNoteAction.SaveNote -> saveNote()
            is AddNoteAction.Cancel -> cancel()
        }
    }

    private fun cancel() {
        scope.launch { 
            _uiEffects.emit(AddNoteUiEffect.NavigateBack)
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
            
            scope.launch {
                val result = notesRepository.addNote(CreateNote(currentState.noteTitle, currentState.noteContent))
                result.fold(
                    onSuccess = {
                        _uiState.value = AddNoteUiState.Content()
                        _uiEffects.emit(AddNoteUiEffect.NavigateBack)
                    },
                    onFailure = { exception ->
                        val errorMessage = exception.message ?: "Unknown error occurred"
                        _uiState.value = AddNoteUiState.Error(errorMessage)
                        _uiEffects.emit(AddNoteUiEffect.ShowError(errorMessage))
                    }
                )
            }
        }
    }
}