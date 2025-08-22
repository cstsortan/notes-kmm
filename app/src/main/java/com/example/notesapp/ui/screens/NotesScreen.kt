package com.example.notesapp.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.data.repository.models.Note
import com.example.notesapp.ui.viewmodel.NotesViewModel


@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(enabled = true, onClick = onClick)
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(8.dp)
    ) {
        Text(text = note.title, style = MaterialTheme.typography.titleMedium)
        Text(text = note.description, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun NoteList(
    notes: List<Note>, modifier: Modifier = Modifier,
    onNoteClicked: (Int) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(notes) { note ->
            NoteItem(
                note = note,
                onClick = { onNoteClicked(note.id) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    notesViewModel: NotesViewModel,
    onEditNoteClicked: (Int) -> Unit = {},
    onAddNewNoteClicked: () -> Unit = {}
) {

    val notes by notesViewModel.notes.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddNewNoteClicked) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notes") },
            )
        }
    ) { innerPadding ->
        NoteList(
            notes = notes,
            modifier = Modifier.padding(innerPadding),
            onNoteClicked = onEditNoteClicked
        )
    }

}