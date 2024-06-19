package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.developers1993.cleanarchitecturenotesapp.R
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.list.component.NotesItem
import com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesListScreen(
    navController: NavHostController?, notesViewmodel: NotesViewmodel = hiltViewModel()
) {

    val notesListState = notesViewmodel.noteState.value

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { data ->
            Snackbar(snackbarData = data)
        }
    }, topBar = {
        TopAppBar(title = {
            Text(text = stringResource(R.string.notes_app))
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { navController?.navigate(Screen.NotesAddEditScreen.route) }) {
            Icon(
                Icons.Rounded.Add, contentDescription = stringResource(R.string.add_notes)
            )
        }
    }) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            if (notesListState.notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.click_on_button_to_add_awesome_notes))
                }
            }else{
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notesListState.notes) { note ->
                        NotesItem(modifier = Modifier.clickable(onClick = {
                            navController?.navigate(Screen.NotesAddEditScreen.route + "?noteId=${note.id}")
                        }), note = note, onDeleteClick = {
                            notesViewmodel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch {
                                val result = snackBarHostState.showSnackbar(
                                    context.getString(R.string.note_deleted_successfully), actionLabel = context.getString(
                                        R.string.undo
                                    )
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    notesViewmodel.onEvent(NotesEvent.RestoreNote(note))
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun NoteScreenPreview() {
    NotesListScreen(null)
}