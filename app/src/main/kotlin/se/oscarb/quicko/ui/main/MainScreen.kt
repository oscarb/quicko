package se.oscarb.quicko.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import se.oscarb.quicko.R
import se.oscarb.quicko.core.model.Note
import se.oscarb.quicko.core.ui.theme.QuickoTheme
import se.oscarb.quicko.ui.main.MainUiState.Success

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state is Success) {
        NotesScreen(
            notes = (state as Success).notes,
            onSave = { name -> viewModel.addNote(name) },
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NotesScreen(
    notes: List<Note>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)

    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val scrollToBottom = {
        scope.launch {
            scrollState.scrollToItem(0)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Quicko") },
                scrollBehavior = scrollBehavior,
            )
        },
        // Exclude IME and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding)
        ) {
            Notes(
                notes = notes,
                scrollState = scrollState,
                modifier = Modifier.weight(1f)
            )

            AddNoteBar(
                onSaveNote = { noteText ->
                    onSave(noteText)
                    scrollToBottom()
                },
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun Notes(
    notes: List<Note>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        reverseLayout = true,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(16.dp),
        state = scrollState,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(notes) {
            NoteItem(it.content)
        }
    }
}

@Composable
private fun NoteItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun AddNoteBar(
    onSaveNote: (text: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val isSaveNoteEnabled = textState.text.isNotBlank()

    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .navigationBarsPadding()
            .imePadding()
    ) {
        val textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)

        BasicTextField(
            value = textState,
            singleLine = false,
            onValueChange = { textState = it },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            keyboardActions = KeyboardActions {
                onSaveNote(textState.text)
                textState = TextFieldValue()
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Send,
                showKeyboardOnFocus = true,
            ),
            textStyle = textStyle,
        ) { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(shape = MaterialTheme.shapes.extraLarge)
                    .background(color = MaterialTheme.colorScheme.surfaceBright)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (textState.text.isEmpty()) {
                        Text(
                            stringResource(R.string.add_note_bar_label),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.9f)
                        )
                    }
                    innerTextField()
                }
            }
        }
        IconButton(
            enabled = isSaveNoteEnabled,
            onClick = {
                onSaveNote(textState.text)
                textState = TextFieldValue()
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.Send,
                contentDescription = stringResource(R.string.save_note_label),
                tint = MaterialTheme.colorScheme.primary.takeIf { isSaveNoteEnabled }
                    ?: LocalContentColor.current,
                modifier = Modifier.padding(0.dp)
            )
        }

    }
}


@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun NoteItemPreview() {
    QuickoTheme {
        NoteItem(text = "Hello world!")
    }
}

@PreviewLightDark
@Composable
private fun AddNoteBarPreview() {
    QuickoTheme {
        AddNoteBar(onSaveNote = {})
    }
}

@PreviewLightDark
@Composable
private fun DefaultPreview() {
    QuickoTheme {
        NotesScreen(
            notes = placeholderNotes.map { Note(it) },
            onSave = {})
    }
}

private val placeholderNotes: List<String> = listOf(
    "Remember to pick up groceries after work.",
    "Grocery list: Milk, eggs, bread",
    "Project brainstorming",
    "Dentist appointment: Oct 26th",
    "Finish chapter 3",
    "Call Mom!",
    "Pay bills",
    "Book doctor appointment for next month's checkup.",
    "Weekend trip ideas?",
    "Investments research",
    "New blog post draft",
    "Dinner recipe: Pasta!",
    "Meditate for 15 minutes",
    "Clean garage",
    "Walk the dog",
    "Learn Spanish online",
    "Review presentation slides for the team meeting.",
    "Update LinkedIn",
    "Car maintenance next week.",
    "Buy gift for friend",
    "Read 30 pages",
    "Community event?",
    "Team meeting agenda",
    "Code personal project",
    "Reflect on today's goals",
    "Graphic design course",
    "Brainstorming session for new project ideas.",
    "Send thank you notes",
    "Hike nearby trails",
    "Backup files",
    "Check the weather",
    "Hydration plan",
    "Project inspiration!",
    "Quick note",
    "Book recommendations?",
    "Long term goals",
    "Short term wins",
    "Ideas",
    "Remember this",
    "Another note here",
    "Meeting feedback"
)