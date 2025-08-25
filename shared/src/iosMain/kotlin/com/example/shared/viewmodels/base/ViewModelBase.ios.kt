package com.example.shared.viewmodels.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

actual class ViewModelBase {
    actual val scope: CoroutineScope
        get() = CoroutineScope(Dispatchers.Main)
}