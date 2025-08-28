package com.example.shared.viewmodels

import com.example.shared.repository.NotesRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DI: KoinComponent {
    fun getNotesViewModel(): NotesViewModel {
        return get<NotesViewModel>()
    }

    fun getNotesRepository(): NotesRepository {
        return get<NotesRepository>()
    }
}