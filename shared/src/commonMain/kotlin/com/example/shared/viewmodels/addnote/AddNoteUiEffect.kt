package com.example.shared.viewmodels.addnote

sealed class AddNoteUiEffect {
    object NavigateBack : AddNoteUiEffect()
    data class ShowError(val message: String) : AddNoteUiEffect()
}