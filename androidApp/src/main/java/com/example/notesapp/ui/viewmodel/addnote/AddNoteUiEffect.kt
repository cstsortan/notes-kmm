package com.example.notesapp.ui.viewmodel.addnote

sealed class AddNoteUiEffect {
    object NavigateBack : AddNoteUiEffect()
    data class ShowError(val message: String) : AddNoteUiEffect()
}