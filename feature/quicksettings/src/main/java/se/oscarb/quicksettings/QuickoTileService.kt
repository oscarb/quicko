package se.oscarb.quicksettings

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.activity.ComponentDialog
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.oscarb.quicko.core.data.NoteRepository
import javax.inject.Inject

@AndroidEntryPoint
class QuickoTileService : TileService() {

    @Inject
    lateinit var noteRepository: NoteRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.apply {
            state = Tile.STATE_INACTIVE
            updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        if (isLocked) {
            unlockAndRun {
                showNoteDialog()
            }
        } else {
            showNoteDialog()
        }
    }

    private fun showNoteDialog() {
        val dialog = ComponentDialog(this, android.R.style.Theme_DeviceDefault_Dialog_Alert)
        dialog.window?.let { window ->
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.decorView.setViewTreeLifecycleOwner(dialog)
            window.decorView.setViewTreeSavedStateRegistryOwner(dialog)
        }

        val composeView = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                QuickoNoteDialogContent(
                    onDismiss = dialog::dismiss,
                ) { noteText ->
                    serviceScope.launch {
                        withContext(Dispatchers.IO + NonCancellable) {
                            noteRepository.add(noteText)
                        }
                        dialog.dismiss()
                    }
                }
            }
        }

        dialog.setContentView(composeView)
        showDialog(dialog)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
