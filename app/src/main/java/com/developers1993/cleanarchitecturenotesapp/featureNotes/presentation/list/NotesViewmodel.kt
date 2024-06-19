package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewmodel @Inject constructor(
    private val notesUseCase: NotesUseCase
) : ViewModel() {

    private val _notes = mutableStateOf(NotesState())
    val noteState: State<NotesState> = _notes

    private var deletedNote: Note? = null

    init {
        getNotesList()
    }

    private fun getNotesList() {
        notesUseCase.notesList().onEach { notes ->
            _notes.value = noteState.value.copy(notes = notes)
        }.launchIn(viewModelScope)
    }

    fun onEvent(notesEvent: NotesEvent) {
        when (notesEvent) {
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesUseCase.deleteNote(notesEvent.note)
                    deletedNote = notesEvent.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCase.addNote(notesEvent.note)
                    deletedNote = null
                }
            }
        }
    }

}

data class NotesState(
    val notes: List<Note> = emptyList()
)

sealed class NotesEvent {
    data class DeleteNote(val note: Note) : NotesEvent()
    data class RestoreNote(val note: Note) : NotesEvent()
}