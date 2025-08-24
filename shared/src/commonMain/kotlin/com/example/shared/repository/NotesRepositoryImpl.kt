package com.example.shared.repository

import com.example.shared.database.dao.NotesDao
import com.example.shared.database.entities.NoteEntity
import com.example.shared.models.CreateNote
import com.example.shared.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val notesDao: NotesDao
): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getAllNotes().map { notes ->
            notes.map { it.toNote() }
        }
    }

    override fun getNote(id: Int): Flow<Note?> {
        return notesDao.getNoteById(id).map { noteEntity ->
            noteEntity?.toNote()
        }
    }

    override suspend fun addNote(note: CreateNote) {
        return notesDao.insertNote(
            title = note.title,
            content = note.description
        )
    }

    override suspend fun updateNote(
        id: Int,
        note: CreateNote
    ) {
        return notesDao.updateNote(
            id = id,
            title = note.title,
            content = note.description
        )
    }
}

private fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        description = content
    )
}
