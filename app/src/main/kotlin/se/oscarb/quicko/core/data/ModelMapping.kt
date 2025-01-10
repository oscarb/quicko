package se.oscarb.quicko.core.data

import se.oscarb.quicko.core.database.LocalNote
import se.oscarb.quicko.core.model.Note

// Domain model to local
fun Note.toLocal() = LocalNote(content = content)

// Local to domain model
fun LocalNote.toDomain() = Note(content = content)

fun List<LocalNote>.toDomain() = map(LocalNote::toDomain)