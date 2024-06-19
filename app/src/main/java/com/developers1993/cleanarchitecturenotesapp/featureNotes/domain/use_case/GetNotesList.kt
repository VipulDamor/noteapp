package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case

import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotesList(private val repository: NoteRepository) {
    operator fun invoke(): Flow<List<Note>> {
        return repository.getNotes()
    }
}