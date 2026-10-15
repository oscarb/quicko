package se.oscarb.quicksettings

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import se.oscarb.quicko.core.ui.theme.QuickoTheme
import se.oscarb.quicko.core.ui.theme.icon.send
import kotlin.time.Duration.Companion.milliseconds

@Composable
internal fun QuickoNoteScreen(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onOpenApp: () -> Unit,
) {
    var isSaved by remember { mutableStateOf(value = false) }
    var isVisible by remember { mutableStateOf(value = false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(20.milliseconds)
        isVisible = true
    }

    val offsetY by animateDpAsState(
        targetValue = if (isVisible) 0.dp else (-180).dp,
        animationSpec = spring(
            dampingRatio = 0.8f,
            stiffness = 400f,
        ),
        label = "offsetY",
    )

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 180),
        label = "alpha",
    )

    fun dismissWithAnimation() {
        if (!isVisible) return
        isVisible = false
        scope.launch {
            delay(180.milliseconds)
            onDismiss()
        }
    }

    val savedMessage = stringResource(id = R.string.quicko_note_saved)
    val openAppLabel = stringResource(id = R.string.quicko_note_open_app)

    QuickoTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { dismissWithAnimation() },
                ),
            contentAlignment = Alignment.TopCenter,
        ) {
            if (!isSaved) {
                Surface(
                    shape = RoundedCornerShape(28.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    tonalElevation = 6.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .offset { IntOffset(0, offsetY.roundToPx()) }
                        .graphicsLayer(alpha = alpha)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {},
                ) {
                    QuickoNoteTopDialogContent(
                        isCardVisible = isVisible,
                        onSave = { noteText ->
                            onSave(noteText)
                            isSaved = true
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = savedMessage,
                                    actionLabel = openAppLabel,
                                    duration = SnackbarDuration.Short,
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onOpenApp()
                                } else {
                                    onDismiss()
                                }
                            }
                        },
                    )
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            )
        }
    }
}

@Composable
private fun QuickoNoteTopDialogContent(
    isCardVisible: Boolean,
    onSave: (String) -> Unit,
) {
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    val focusRequester = remember { FocusRequester() }
    val isSaveNoteEnabled = textState.text.isNotBlank()

    LaunchedEffect(isCardVisible) {
        if (isCardVisible) {
            delay(120.milliseconds)
            focusRequester.requestFocus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_quicko_note_tile),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.quicko_note_dialog_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.extraLarge)
                .background(color = MaterialTheme.colorScheme.surfaceBright)
                .padding(vertical = 4.dp, horizontal = 16.dp),
        ) {
            val textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface)

            BasicTextField(
                value = textState,
                singleLine = false,
                onValueChange = { textState = it },
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
                    .focusRequester(focusRequester),
                keyboardActions = KeyboardActions(
                    onSend = {
                        val text = textState.text.trim()
                        if (text.isNotEmpty()) {
                            onSave(text)
                            textState = TextFieldValue()
                        }
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send,
                    showKeyboardOnFocus = true,
                ),
                textStyle = textStyle,
            )

            IconButton(
                enabled = isSaveNoteEnabled,
                onClick = {
                    val text = textState.text.trim()
                    if (text.isNotEmpty()) {
                        onSave(text)
                        textState = TextFieldValue()
                    }
                },
            ) {
                Icon(
                    imageVector = send,
                    contentDescription = stringResource(R.string.save),
                    tint = MaterialTheme.colorScheme.primary.takeIf { isSaveNoteEnabled }
                        ?: LocalContentColor.current,
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuickoNoteTopDialogContentPreview() {
    QuickoTheme {
        QuickoNoteTopDialogContent(
            isCardVisible = true,
            onSave = {},
        )
    }
}
