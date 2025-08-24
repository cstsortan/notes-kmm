package com.example.notesapp.ui.router

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    object Notes : Screen()

    @Serializable
    object AddNote : Screen()

    @Serializable
    data class EditNote(val id: Int) : Screen()
}
