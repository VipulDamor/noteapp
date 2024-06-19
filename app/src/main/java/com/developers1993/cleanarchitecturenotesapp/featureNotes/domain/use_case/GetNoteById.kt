package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case

import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository

class GetNoteById(private val notesRepository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note? {
        return notesRepository.getNoteById(id)
    }
}