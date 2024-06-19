package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case

import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository

class DeleteNote (private val repository: NoteRepository){

    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}