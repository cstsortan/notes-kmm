package com.example.shared.repository

import com.example.shared.models.CreateNote
import com.example.shared.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note?>
    suspend fun addNote(note: CreateNote)
    suspend fun updateNote(id: Int, note: CreateNote)
}

class InMemoryNotesRepository : NotesRepository {
    private var notes = MutableStateFlow<List<Note>>(
        emptyList()
    )

    override fun getNotes(): Flow<List<Note>> {
        return notes
    }

    override fun getNote(id: Int): Flow<Note?> {
        return notes.map { noteList ->
            noteList.find { it.id == id }
        }
    }

    override suspend fun addNote(note: CreateNote) {
        val newNote = Note(
            id = notes.value.size + 1,
            title = note.title,
            description = note.description
        )
        notes.value = notes.value + newNote
    }

    override suspend fun updateNote(id: Int, note: CreateNote) {
        notes.value = notes.value.map {
            if (it.id == id) {
                Note(
                    id = it.id,
                    title = note.title,
                    description = note.description
                )
            } else {
                it
            }
        }
    }
}