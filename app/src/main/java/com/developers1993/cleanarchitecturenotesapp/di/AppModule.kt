package com.developers1993.cleanarchitecturenotesapp.di

import android.app.Application
import androidx.room.Room
import com.developers1993.cleanarchitecturenotesapp.featureNotes.data.repository.NoteRepositoryImpl
import com.developers1993.cleanarchitecturenotesapp.featureNotes.data.source.NotesDatabase
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.repository.NoteRepository
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.AddNote
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.DeleteNote
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.GetNoteById
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.GetNotesList
import com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.use_case.NotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDB(app: Application): NotesDatabase {
        return Room.databaseBuilder(app, NotesDatabase::class.java, NotesDatabase.DATABASE_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNOtesRepository(db: NotesDatabase): NoteRepository {
        return NoteRepositoryImpl(db.notesDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(repository: NoteRepository): NotesUseCase {
        return NotesUseCase(
            addNote = AddNote(repository),
            noteById = GetNoteById(repository),
            notesList = GetNotesList(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}