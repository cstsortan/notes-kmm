package com.example.shared.viewmodels.base

import kotlinx.coroutines.CoroutineScope

expect abstract class ViewModelBase() {
    val scope: CoroutineScope
}