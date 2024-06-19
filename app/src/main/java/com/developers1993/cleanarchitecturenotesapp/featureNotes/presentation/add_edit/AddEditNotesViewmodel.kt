package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.InvalidNoteException
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.NotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditNotesViewmodel @Inject constructor(
    private val notesUseCase: NotesUseCase, savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _title = mutableStateOf(TextFieldState(hint = "Title"))
    val title: State<TextFieldState> = _title

    private val _details = mutableStateOf(TextFieldState(hint = "Notes"))
    val details: State<TextFieldState> = _details

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { listNoteID ->
            if (listNoteID != -1) {
                viewModelScope.launch(Dispatchers.IO) {
                    notesUseCase.noteById(listNoteID)?.also { note ->
                        withContext(Dispatchers.Main) {
                            currentNoteId = note.id
                            _title.value =
                                _title.value.copy(text = note.title, isHintVisible = false)
                            _details.value =
                                _details.value.copy(text = note.description, isHintVisible = false)
                        }
                    }

                }
            }
        }
    }

    fun onEvent(event: AddEditNotesEvent) {
        when (event) {
            is AddEditNotesEvent.OnDescriptionTextChange -> {
                _details.value = _details.value.copy(
                    text = event.value, isHintVisible = isHIntVisible(event.value)
                )
            }

            is AddEditNotesEvent.OnTitleTextChange -> {
                _title.value = _title.value.copy(
                    text = event.value, isHintVisible = isHIntVisible(event.value)
                )

            }

            is AddEditNotesEvent.SaveNotes -> {
                viewModelScope.launch {
                    try {
                        val note = Note(
                            title = title.value.text,
                            description = details.value.text,
                            timestamp = System.currentTimeMillis(),
                            color = Color(0xFFD7E8DE).toArgb(),
                            id = currentNoteId,
                        )
                        notesUseCase.addNote(note)
                        _eventFlow.emit(UiEvents.SaveNotes)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvents.ShowSnackBar(e.message ?: "can't save notes"))
                    }
                }

            }
        }
    }

    private fun isHIntVisible(value: String): Boolean {
        return value.isEmpty()
    }

}

sealed class UiEvents {
    data class ShowSnackBar(val message: String) : UiEvents()
    data object SaveNotes : UiEvents()
}