package com.example.shared.viewmodels.addnote

sealed class AddNoteUiState {
    data class Content(
        val noteTitle: String = "",
        val noteContent: String = ""
    ) : AddNoteUiState()
    
    object Loading : AddNoteUiState()
    data class Error(val message: String) : AddNoteUiState()
}