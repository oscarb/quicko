package se.oscarb.quicko

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import se.oscarb.quicko.ui.main.MainScreen

@Serializable
object Main

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Main, modifier = modifier) {
        composable<Main> { MainScreen(modifier = modifier) }
    }
}