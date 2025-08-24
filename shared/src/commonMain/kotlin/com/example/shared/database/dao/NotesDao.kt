package com.example.shared.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.shared.database.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity?>

    @Query("INSERT INTO notes (title, content) VALUES (:title, :content)")
    suspend fun insertNote(title: String, content: String)

    @Query("UPDATE notes SET title = :title, content = :content WHERE id = :id")
    suspend fun updateNote(id: Int, title: String, content: String)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)
}
