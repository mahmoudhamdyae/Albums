package com.mahmoudhamdyae.albums

import androidx.activity.compose.setContent
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.mahmoudhamdyae.albums.ui.AlbumsApp
import com.mahmoudhamdyae.albums.ui.MainActivity
import com.mahmoudhamdyae.albums.ui.screens.album.AlbumScreen
import com.mahmoudhamdyae.albums.util.TestTags
import org.junit.Rule
import org.junit.Test

class AlbumsEndToEndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun startHomeScreen_verifyContent() {
        // When HomeScreen is loaded
        composeTestRule.activity.setContent { AlbumsApp() }

        // Then all the contents are displayed on the screen.
        composeTestRule.onNodeWithTag(TestTags.LOADING).assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))
        composeTestRule.onNodeWithTag(TestTags.USER_CONTENT).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.ALBUMS_CONTENT).assertIsDisplayed()
    }

    @Test
    @OptIn(ExperimentalTestApi::class)
    fun startAlbumScreen_verifyContent() {
        // When AlbumScreen is loaded
        composeTestRule.activity.setContent {
            AlbumScreen(
                albumId = "1",
                albumTitle = "accusamus beatae ad facilis cum similique qui sunt",
                navigateUp = { }
            )
        }

        // Then all the contents are displayed on the screen.
        composeTestRule.onNodeWithTag(TestTags.LOADING).assertIsDisplayed()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))
        composeTestRule.onNodeWithText("accusamus beatae ad facilis cum similique qui sunt").assertExists()
        composeTestRule.onNodeWithStringId(R.string.search_hint).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.ALBUMS_PHOTOS).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun startAlbumsScreen_search() {
        // When AlbumScreen is loaded
        composeTestRule.activity.setContent {
            AlbumScreen(
                albumId = "1",
                albumTitle = "accusamus beatae ad facilis cum similique qui sunt",
                navigateUp = { }
            )
        }

        // Then all the contents are displayed on the screen.
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.LOADING))
        composeTestRule.onNodeWithStringId(R.string.empty_text).assertDoesNotExist()
        composeTestRule.onNodeWithStringId(R.string.search_hint).performClick().performTextInput("abcdef")
        composeTestRule.onNodeWithStringId(R.string.empty_text).assertIsDisplayed()
    }
}