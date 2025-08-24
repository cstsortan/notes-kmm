package com.example.notesapp

import android.app.Application
import com.example.notesapp.di.databaseModule
import com.example.notesapp.di.repositoryModule
import com.example.notesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@NotesApplication)
            modules(listOf(
                databaseModule,
                repositoryModule,
                viewModelModule,
            ))
        }
    }
}