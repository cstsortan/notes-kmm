package com.example.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.example.shared.database.dao.NotesDao
import com.example.shared.database.entities.NoteEntity

@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object NotesDatabaseConstructor : RoomDatabaseConstructor<NotesDatabase> {
    override fun initialize(): NotesDatabase
}

@Database(entities = [NoteEntity::class], version = 1)
@ConstructedBy(NotesDatabaseConstructor::class)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}
