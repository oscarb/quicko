package se.oscarb.quicksettings

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.oscarb.quicko.core.data.NoteRepository
import javax.inject.Inject

@AndroidEntryPoint
class QuickoNoteActivity : ComponentActivity() {

    @Inject
    lateinit var noteRepository: NoteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        window.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
        window.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )

        setContent {
            QuickoNoteScreen(
                onDismiss = ::finish,
                onSave = { noteText ->
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO + NonCancellable) {
                            noteRepository.add(noteText)
                        }
                    }
                },
                onOpenApp = {
                    val launchIntent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    launchIntent?.let { startActivity(it) }
                    finish()
                },
            )
        }
    }
}
