package se.oscarb.quicksettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.oscarb.quicko.core.ui.theme.QuickoTheme

@Composable
internal fun QuickoNoteDialogContent(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    var noteText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    QuickoTheme {
        Surface(
            shape = AlertDialogDefaults.shape,
            color = AlertDialogDefaults.containerColor,
            tonalElevation = AlertDialogDefaults.TonalElevation,
            modifier = Modifier.padding(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.quicko_note_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    color = AlertDialogDefaults.titleContentColor,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    placeholder = {
                        Text(text = stringResource(id = R.string.quicko_note_hint))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        showKeyboardOnFocus = true,
                    ),
                    keyboardActions = KeyboardActions {
                        val text = noteText.trim()
                        if (text.isNotEmpty()) {
                            onSave(text)
                        }
                    },
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            val text = noteText.trim()
                            if (text.isNotEmpty()) {
                                onSave(text)
                            }
                        },
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuickoNoteDialogContentPreview() {
    QuickoTheme {
        QuickoNoteDialogContent(
            onDismiss = {},
        ) {}
    }
}