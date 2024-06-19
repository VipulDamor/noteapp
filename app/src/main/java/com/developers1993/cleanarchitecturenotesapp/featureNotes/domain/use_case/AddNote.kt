package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case

import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.InvalidNoteException
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository


class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("title can't be empty")
        }
        if (note.description.isBlank()) {
            throw InvalidNoteException("description can't be empty")
        }
        repository.insertNote(note)
    }
}