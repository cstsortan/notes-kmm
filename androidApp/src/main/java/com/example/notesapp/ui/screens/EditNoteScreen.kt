package com.example.notesapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notesapp.ui.viewmodel.EditNoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    modifier: Modifier = Modifier,
    noteId: String,
    onPop: () -> Unit = {},
    editNoteViewModel: EditNoteViewModel,
) {
    val title by editNoteViewModel.noteTitle.collectAsState()
    val description by editNoteViewModel.noteDescription.collectAsState()

    LaunchedEffect(noteId) {
        editNoteViewModel.initialize()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onPop) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            TextField(
                value = title,
                onValueChange = {
                    editNoteViewModel.setNoteTitle(it)
                },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = {
                    editNoteViewModel.setNoteDescription(it)
                },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    editNoteViewModel.saveNote()
                    onPop()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update Note")
            }

        }
    }
}