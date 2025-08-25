package com.example.shared.viewmodels.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class ViewModelBase: ViewModel() {
    actual val scope: CoroutineScope = viewModelScope
}