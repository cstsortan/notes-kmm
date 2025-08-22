package com.example.notesapp.di

import com.example.notesapp.data.repository.InMemoryNotesRepository
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<NotesRepository> { NotesRepositoryImpl(get()) }
}