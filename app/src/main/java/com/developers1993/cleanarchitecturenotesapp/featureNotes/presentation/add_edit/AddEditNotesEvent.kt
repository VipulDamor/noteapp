package com.developers1993.cleanarchitecturenotesapp.featureNotes.presentation.add_edit

sealed class AddEditNotesEvent {
    data class OnTitleTextChange(val value:String):AddEditNotesEvent()
    data class OnDescriptionTextChange(val value: String):AddEditNotesEvent()
    data object SaveNotes : AddEditNotesEvent()
}