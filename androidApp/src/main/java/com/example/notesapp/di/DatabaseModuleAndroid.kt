package com.example.notesapp.di

import com.example.shared.database.notesDatabase
import org.koin.dsl.module

val databaseModuleAndroid = module {
    single {
        val context = get<android.content.Context>()
        notesDatabase(context)
    }
}

