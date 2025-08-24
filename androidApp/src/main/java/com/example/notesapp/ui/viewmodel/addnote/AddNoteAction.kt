package com.example.notesapp.ui.viewmodel.addnote

sealed class AddNoteAction {
    data class UpdateTitle(val title: String) : AddNoteAction()
    data class UpdateContent(val content: String) : AddNoteAction()
    object ClearFields : AddNoteAction()
    object SaveNote : AddNoteAction()
}