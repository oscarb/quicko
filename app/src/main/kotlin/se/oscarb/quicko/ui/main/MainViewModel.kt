package se.oscarb.quicko.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import se.oscarb.quicko.core.data.NoteRepository
import se.oscarb.quicko.core.model.Note
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val uiState: StateFlow<MainUiState> = noteRepository.notes
        .map<List<Note>, MainUiState> { MainUiState.Success(notes = it) }
        .catch { emit(MainUiState.Error(it)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MainUiState.Loading
        )

    fun addNote(content: String) {
        viewModelScope.launch {
            noteRepository.add(content)
        }
    }

}

sealed interface MainUiState {
    object Loading : MainUiState
    data class Error(val throwable: Throwable) : MainUiState
    data class Success(val notes: List<Note>) : MainUiState
}