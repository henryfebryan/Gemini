package com.henry.gemini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.henry.gemini.ui.screen.ChatScreen
import com.henry.gemini.ui.screen.HomeScreen
import com.henry.gemini.ui.screen.ImagePromptScreen
import com.henry.gemini.ui.screen.JsonScreen
import com.henry.gemini.ui.screen.NavigationItem
import com.henry.gemini.ui.screen.TextPromptScreen
import com.henry.gemini.ui.theme.GeminiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeminiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController)
        }
        composable(NavigationItem.TextPrompt.route) {
            TextPromptScreen(navController)
        }
        composable(NavigationItem.ImagePrompt.route) {
            ImagePromptScreen(navController)
        }
        composable(NavigationItem.Chat.route) {
            ChatScreen(navController)
        }
        composable(NavigationItem.Json.route) {
            JsonScreen(navController)
        }
    }
}
