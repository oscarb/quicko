package se.oscarb.quicko.core.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DefaultNoteRepositoryTest {
    @Test
    fun `when new item is saved item is returned`() = runTest {
        val repository = DefaultNoteRepository()

        repository.add("Test note")

        assertEquals(repository.notes.first().size, 1)
    }
}