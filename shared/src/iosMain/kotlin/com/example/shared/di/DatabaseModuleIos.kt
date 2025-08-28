package com.example.shared.di

import com.example.shared.database.NotesDatabase
import com.example.shared.database.getPersistentDatabase
import org.koin.dsl.module

val databaseModuleIos = module {
    single<NotesDatabase> {
        getPersistentDatabase()
    }
}