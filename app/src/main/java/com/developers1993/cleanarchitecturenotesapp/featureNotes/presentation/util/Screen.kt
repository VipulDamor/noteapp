package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.util

sealed class Screen(val route:String) {
    data object NotesListScreen : Screen("notes_list_screen")
    data object NotesAddEditScreen : Screen("add_edit_screen")
}