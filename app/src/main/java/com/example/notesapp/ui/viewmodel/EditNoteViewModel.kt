package com.example.notesapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.models.CreateNote
import com.example.notesapp.ui.router.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    val notesRepository: NotesRepository
) : ViewModel() {

    private val noteId = savedStateHandle.toRoute<Screen.EditNote>().id

    private val _noteTitle = MutableStateFlow("")
    private val _noteDescription = MutableStateFlow("")

    val noteTitle = _noteTitle.asStateFlow()
    val noteDescription = _noteDescription.asStateFlow()

    fun initialize() {
        // Get the note by id and set the title and description
        viewModelScope.launch {
            val note = notesRepository.getNote(noteId).first()
            _noteTitle.value = note?.title ?: ""
            _noteDescription.value = note?.description ?: ""
        }
    }

    fun setNoteTitle(title: String) {
        _noteTitle.value = title
    }

    fun setNoteDescription(description: String) {
        _noteDescription.value = description
    }

    fun clearFields() {
        _noteTitle.value = ""
        _noteDescription.value = ""
    }

    fun saveNote() {
        // Logic to save the note
        // This could involve calling a repository or database
        viewModelScope.launch {
            notesRepository.updateNote(
                id = noteId,
                CreateNote(_noteTitle.value, _noteDescription.value)
            )
        }
    }
}