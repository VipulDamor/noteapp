package com.developers1993.cleanarchitecturenotesapp.featureNotes.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract val notesDao: NotesDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}