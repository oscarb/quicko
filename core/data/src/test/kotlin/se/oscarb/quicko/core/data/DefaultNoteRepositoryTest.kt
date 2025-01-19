package se.oscarb.quicko.core.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import se.oscarb.quicko.core.database.LocalNote
import se.oscarb.quicko.core.database.NoteDao

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultNoteRepositoryTest {
    @Test
    fun `when new item is saved item is returned`() = runTest {
        val repository = DefaultNoteRepository(FakeNoteDao())

        repository.add("Test note")

        Assert.assertEquals(repository.notes.first().size, 1)
    }
}

private class FakeNoteDao : NoteDao {
    private val data = mutableListOf<LocalNote>()

    override fun getNotes(): Flow<List<LocalNote>> = flow {
        emit(data)
    }

    override suspend fun insert(note: LocalNote) {
        data.add(0, note)
    }
}