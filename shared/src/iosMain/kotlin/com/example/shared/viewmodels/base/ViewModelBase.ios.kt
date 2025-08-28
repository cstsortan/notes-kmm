package com.example.shared.viewmodels.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual abstract class ViewModelBase actual constructor() {
    actual val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main)
}