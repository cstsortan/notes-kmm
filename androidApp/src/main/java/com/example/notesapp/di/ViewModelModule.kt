package com.example.notesapp.di

import com.example.notesapp.ui.viewmodel.AddNoteViewModel
import com.example.notesapp.ui.viewmodel.EditNoteViewModel
import com.example.notesapp.ui.viewmodel.NotesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NotesViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { EditNoteViewModel(get(), get()) }
}