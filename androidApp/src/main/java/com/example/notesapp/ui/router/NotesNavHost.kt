package com.example.notesapp.ui.router

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.notesapp.ui.screens.AddNoteScreen
import com.example.notesapp.ui.screens.EditNoteScreen
import com.example.notesapp.ui.screens.NotesScreen
import com.example.shared.viewmodels.NotesViewModel
import com.example.shared.viewmodels.addnote.AddNoteViewModel
import com.example.shared.viewmodels.editnote.EditNoteViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NotesNavHost(
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
            val editNoteViewModel: EditNoteViewModel = koinViewModel(
                parameters = { parametersOf(backStackEntry.toRoute<Screen.EditNote>().id) }
            )
            EditNoteScreen(
                editNoteViewModel = editNoteViewModel,
                onPop = { navController.popBackStack() }
            )

        }
    }
}