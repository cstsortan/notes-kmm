package com.example.notesapp.ui.viewmodel.editnote

sealed class EditNoteUiEffect {
    object NavigateBack : EditNoteUiEffect()
    data class ShowError(val message: String) : EditNoteUiEffect()
}