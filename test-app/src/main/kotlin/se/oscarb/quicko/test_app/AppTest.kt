package se.oscarb.quicko.test_app

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import se.oscarb.quicko.MainActivity
import se.oscarb.quicko.core.data.di.fakeNotes

@HiltAndroidTest
class AppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test() {
        composeTestRule.onNodeWithText(fakeNotes.first().content, substring = true).assertExists()
    }

}