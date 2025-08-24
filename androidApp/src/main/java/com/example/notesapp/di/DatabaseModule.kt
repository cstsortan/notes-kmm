package com.example.notesapp.di

import com.example.shared.database.NotesDatabase
import com.example.shared.database.dao.NotesDao
import com.example.shared.database.notesDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        val context = get<android.content.Context>()
        notesDatabase(context)
    }
    single<NotesDao> { get<NotesDatabase>().notesDao() }
}

