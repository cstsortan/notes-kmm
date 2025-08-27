package com.example.notesapp

import android.app.Application
import com.example.notesapp.di.databaseModuleAndroid
import com.example.shared.di.initKoin
import org.koin.android.ext.koin.androidContext

class NotesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NotesApplication)
            modules(listOf(
                databaseModuleAndroid,
            ))
        }
    }
}