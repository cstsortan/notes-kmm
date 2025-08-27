package com.example.shared.di

import com.example.shared.viewmodels.NotesViewModel
import com.example.shared.viewmodels.addnote.AddNoteViewModel
import com.example.shared.viewmodels.editnote.EditNoteViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory<NotesViewModel> { NotesViewModel(get()) }
    factory<AddNoteViewModel> { AddNoteViewModel(get()) }
    factory<EditNoteViewModel> { parameters ->
        EditNoteViewModel(parameters.get(), get())
    }
}