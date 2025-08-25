package com.example.shared.viewmodels.editnote

sealed class EditNoteUiEffect {
    object NavigateBack : EditNoteUiEffect()
    data class ShowError(val message: String) : EditNoteUiEffect()
}