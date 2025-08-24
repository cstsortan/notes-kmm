package com.example.notesapp.ui.viewmodel.editnote

sealed class EditNoteUiState {
    data class Content(
        val noteTitle: String = "",
        val noteContent: String = ""
    ) : EditNoteUiState()
    
    object Loading : EditNoteUiState()
    data class Error(val message: String) : EditNoteUiState()
}