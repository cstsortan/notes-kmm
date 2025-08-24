package com.example.notesapp.ui.viewmodel.editnote

sealed class EditNoteAction {
    data class UpdateTitle(val title: String) : EditNoteAction()
    data class UpdateContent(val content: String) : EditNoteAction()
    object ClearFields : EditNoteAction()
    object SaveNote : EditNoteAction()
    object Initialize : EditNoteAction()
}