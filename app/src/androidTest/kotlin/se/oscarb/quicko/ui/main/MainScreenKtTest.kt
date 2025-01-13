package se.oscarb.quicko.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import se.oscarb.quicko.core.model.Note

@RunWith(AndroidJUnit4::class)
class MainScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            NotesScreen(
                notes = fakeNotes,
                onSave = {}
            )
        }
    }

    @Test
    fun `first item exists`() {
        composeTestRule.onNodeWithText(fakeNotes.first().content).assertExists()
    }
}

private val fakeNotes = listOf(Note("Note 1"), Note("Note 2"))