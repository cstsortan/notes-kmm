package com.example.notesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.data.database.dao.NotesDao
import com.example.notesapp.data.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
