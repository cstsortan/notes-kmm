package com.example.shared.di

fun startKoinIos() {
    initKoin {
        modules(listOf(
            databaseModuleIos
        ))
    }
}