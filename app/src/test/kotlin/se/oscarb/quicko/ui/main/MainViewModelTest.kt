package se.oscarb.quicko.ui.main

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import se.oscarb.quicko.core.data.NoteRepository
import se.oscarb.quicko.core.model.Note

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Test
    fun `uiState initially Loading`() = runTest {
        val viewModel = MainViewModel(FakeNoteRepository())
        assertEquals(viewModel.uiState.first(), MainUiState.Loading)
    }
}

private class FakeNoteRepository() : NoteRepository {
    private val data = mutableListOf<Note>()

    override val notes: Flow<List<Note>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(content: String) {
        data.add(0, Note(content))
    }
}


