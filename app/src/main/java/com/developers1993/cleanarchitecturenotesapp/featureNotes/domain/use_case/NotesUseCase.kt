package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case

data class NotesUseCase(
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val noteById: GetNoteById,
    val notesList: GetNotesList
)
