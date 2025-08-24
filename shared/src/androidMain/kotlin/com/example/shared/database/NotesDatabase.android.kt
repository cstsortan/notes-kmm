package com.example.shared.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun notesDatabase(context: Context): NotesDatabase {
    val dbFile = context.getDatabasePath("notes_database.db")
    return Room.databaseBuilder<NotesDatabase>(context, dbFile.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .build()
}