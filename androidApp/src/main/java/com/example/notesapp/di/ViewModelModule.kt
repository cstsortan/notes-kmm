package com.example.notesapp.di

import com.example.shared.viewmodels.NotesViewModel
import com.example.shared.viewmodels.addnote.AddNoteViewModel
import com.example.shared.viewmodels.editnote.EditNoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { NotesViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
    viewModel { parameters ->
        EditNoteViewModel(parameters.get(), get())
    }
}