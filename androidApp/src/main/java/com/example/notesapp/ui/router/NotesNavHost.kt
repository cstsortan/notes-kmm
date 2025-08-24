package com.example.notesapp.ui.router

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notesapp.ui.screens.AddNoteScreen
import com.example.notesapp.ui.viewmodel.addnote.AddNoteViewModel
import com.example.notesapp.ui.screens.EditNoteScreen
import com.example.notesapp.ui.viewmodel.EditNoteViewModel
import com.example.notesapp.ui.screens.NotesScreen
import com.example.notesapp.ui.viewmodel.NotesViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Notes) {
        composable<Screen.Notes> { backStackEntry ->
            val notesViewModel: NotesViewModel = koinViewModel()
            NotesScreen(
                onAddNewNoteClicked = {
                    navController.navigate(Screen.AddNote)
                },
                onEditNoteClicked = {
                    navController.navigate(Screen.EditNote(it))
                },
                notesViewModel = notesViewModel
            )
        }
        composable<Screen.AddNote> { backStackEntry ->
            val addNoteViewModel: AddNoteViewModel = koinViewModel()
            AddNoteScreen(
                addNoteViewModel = addNoteViewModel,
                onPop = { navController.popBackStack() }
            )
        }
        composable<Screen.EditNote> { backStackEntry ->
            val editNoteViewModel: EditNoteViewModel = koinViewModel()
            EditNoteScreen(
                noteId = backStackEntry.id,
                editNoteViewModel = editNoteViewModel,
                onPop = { navController.popBackStack() }
            )

        }
    }
}