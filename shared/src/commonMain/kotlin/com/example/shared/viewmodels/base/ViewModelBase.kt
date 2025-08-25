package com.example.shared.viewmodels.base

import kotlinx.coroutines.CoroutineScope

expect open class ViewModelBase() {
    val scope: CoroutineScope
}