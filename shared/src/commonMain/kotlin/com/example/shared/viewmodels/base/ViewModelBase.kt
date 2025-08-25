package com.example.shared.viewmodels.base

import kotlinx.coroutines.CoroutineScope

expect class ViewModelBase {
    val scope: CoroutineScope
}