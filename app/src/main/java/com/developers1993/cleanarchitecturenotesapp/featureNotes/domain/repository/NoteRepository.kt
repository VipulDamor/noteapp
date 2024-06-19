package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository

import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun getNoteById(id: Int): Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)

}