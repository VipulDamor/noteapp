package com.developers1993.cleanarchitecturenotesapp.featureNotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val description: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)

class InvalidNoteException(message: String): Exception(message)
