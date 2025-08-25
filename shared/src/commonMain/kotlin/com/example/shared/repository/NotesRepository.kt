package com.example.shared.repository

import com.example.shared.models.CreateNote
import com.example.shared.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note?>
    suspend fun addNote(note: CreateNote): Result<Unit>
    suspend fun updateNote(id: Int, note: CreateNote): Result<Unit>
}
