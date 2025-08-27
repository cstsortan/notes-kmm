package com.example.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(declaration: KoinAppDeclaration) {
    startKoin {
        declaration()
        daosModule
        repositoryModule
        viewModelModule
    }
}