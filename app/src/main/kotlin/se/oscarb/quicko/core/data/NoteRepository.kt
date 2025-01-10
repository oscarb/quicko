package se.oscarb.quicko.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.oscarb.quicko.core.database.LocalNote
import se.oscarb.quicko.core.database.NoteDao
import se.oscarb.quicko.core.model.Note
import javax.inject.Inject

interface NoteRepository {
    val notes: Flow<List<Note>>

    suspend fun add(content: String)
}

class DefaultNoteRepository @Inject constructor(
    val noteDao: NoteDao
) : NoteRepository {
    private val _notes = noteDao.getNotes()
    override val notes: Flow<List<Note>> = _notes.map { it.toDomain() }

    override suspend fun add(content: String) {
        noteDao.insert(LocalNote(content = content))
    }
}