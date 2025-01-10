package se.oscarb.quicko.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import se.oscarb.quicko.core.data.DefaultNoteRepository
import se.oscarb.quicko.core.data.NoteRepository
import se.oscarb.quicko.core.model.Note
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsNoteRepository(noteRepository: DefaultNoteRepository): NoteRepository

}

class FakeNoteRepository @Inject constructor() : NoteRepository {
    override val notes: Flow<List<Note>> = flowOf(fakeNotes)

    override suspend fun add(content: String) {
        throw NotImplementedError()
    }

}

val fakeNotes = listOf(
    Note("one"),
    Note("two"),
    Note("three")
)
