package com.example.notesapp.di

import com.example.shared.repository.NotesRepository
import com.example.shared.repository.NotesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}