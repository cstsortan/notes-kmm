package com.example.notesapp.ui.viewmodel.addnote

import com.example.shared.repository.NotesRepository
import com.example.shared.models.CreateNote
import com.example.shared.models.Note
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockNotesRepository : NotesRepository {
    var shouldThrowError = false
    var errorMessage = "Test error"
    var shouldDelayAddNote = false
    var addNoteCalled = false
    var lastCreateNote: CreateNote? = null
    
    private val notes = mutableListOf<Note>()

    override fun getNotes(): Flow<List<Note>> {
        return flowOf(notes.toList())
    }

    override fun getNote(id: Int): Flow<Note?> {
        return flowOf(notes.find { it.id == id })
    }

    override suspend fun addNote(note: CreateNote): Result<Unit> {
        if (shouldDelayAddNote) {
            delay(100)
        }
        
        if (shouldThrowError) {
            return Result.failure(Exception(errorMessage))
        }
        
        addNoteCalled = true
        lastCreateNote = note
        
        val newNote = Note(
            id = notes.size + 1,
            title = note.title,
            description = note.description
        )
        notes.add(newNote)
        return Result.success(Unit)
    }

    override suspend fun updateNote(id: Int, note: CreateNote): Result<Unit> {
        if (shouldThrowError) {
            return Result.failure(Exception(errorMessage))
        }
        
        val index = notes.indexOfFirst { it.id == id }
        if (index != -1) {
            notes[index] = Note(
                id = id,
                title = note.title,
                description = note.description
            )
        }
        return Result.success(Unit)
    }
}