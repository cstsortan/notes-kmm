package com.example.notesapp.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.data.database.NotesDatabase
import com.example.notesapp.data.database.dao.NotesDao
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass= NotesDatabase::class.java,
            name = "notes_database"
        ).build()
    }
    single<NotesDao> { get<NotesDatabase>().notesDao() }
}
