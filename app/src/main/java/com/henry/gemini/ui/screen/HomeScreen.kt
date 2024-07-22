package com.henry.gemini.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home Screen") })
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { navController.navigate(NavigationItem.TextPrompt.route) }
                ) {
                    Text("Text Prompt")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { navController.navigate(NavigationItem.ImagePrompt.route) }
                ) {
                    Text("Text Image Prompt")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { navController.navigate(NavigationItem.Chat.route) }
                ) {
                    Text("Chat")
                }
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { navController.navigate(NavigationItem.Json.route) }
                ) {
                    Text("JSON")
                }
            }
        }
    )
}
