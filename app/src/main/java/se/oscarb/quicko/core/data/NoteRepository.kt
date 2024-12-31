package se.oscarb.quicko.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import se.oscarb.quicko.core.model.Note

interface NoteRepository {
    val notes: Flow<List<Note>>

    suspend fun add(content: String)
}

class DefaultNoteRepository : NoteRepository {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    override val notes: Flow<List<Note>> = _notes

    override suspend fun add(content: String) {
        val updatedNotes = _notes.value + Note(content = content)
        _notes.value = updatedNotes
    }
}