package com.example.shared.di

import com.example.shared.database.NotesDatabase
import com.example.shared.database.dao.NotesDao
import org.koin.dsl.module

val daosModule = module {
    single<NotesDao> { get<NotesDatabase>().notesDao() }
}