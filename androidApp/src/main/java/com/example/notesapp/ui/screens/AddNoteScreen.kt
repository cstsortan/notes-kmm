package com.example.notesapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shared.viewmodels.addnote.AddNoteAction
import com.example.shared.viewmodels.addnote.AddNoteUiEffect
import com.example.shared.viewmodels.addnote.AddNoteUiState
import com.example.shared.viewmodels.addnote.AddNoteViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    modifier: Modifier = Modifier,
    addNoteViewModel: AddNoteViewModel,
    onPop: () -> Unit = {},
) {
    val uiState by addNoteViewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(addNoteViewModel.uiEffects) {
        addNoteViewModel.uiEffects.collectLatest { effect ->
            when (effect) {
                is AddNoteUiEffect.NavigateBack -> onPop()
                is AddNoteUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(
                        message = effect.message
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState
            )
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add Note")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            addNoteViewModel.onAction(AddNoteAction.Cancel)
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when (val state = uiState) {
                is AddNoteUiState.Content -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TextField(
                            value = state.noteTitle,
                            onValueChange = {
                                addNoteViewModel.onAction(AddNoteAction.UpdateTitle(it))
                            },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = state.noteContent,
                            onValueChange = {
                                addNoteViewModel.onAction(AddNoteAction.UpdateContent(it))
                            },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                addNoteViewModel.onAction(AddNoteAction.SaveNote)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = state.noteTitle.isNotBlank()
                        ) {
                            Text("Add Note")
                        }
                    }
                }

                is AddNoteUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is AddNoteUiState.Error -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                addNoteViewModel.onAction(AddNoteAction.ClearFields)
                            }
                        ) {
                            Text("Try Again")
                        }
                    }
                }
            }
        }
    }
}