package com.developers1993.cleanarchitecturenotesapp.featureNotes.data.repository

import com.developers1993.cleanarchitecturenotesapp.featureNotes.data.source.NotesDao
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val notesDao: NotesDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notesDao.noteByID(id)
    }

    override suspend fun insertNote(note: Note) {
        notesDao.addNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNOte(note)
    }
}