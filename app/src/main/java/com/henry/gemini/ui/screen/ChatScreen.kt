package com.henry.gemini.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.henry.gemini.R
import com.henry.gemini.UiState
import com.henry.gemini.ui.viewModel.ChatViewModel

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: ChatViewModel = viewModel()
) {
    var prompt by rememberSaveable { mutableStateOf("") }
    val isLoading = viewModel.isLoading.collectAsState(false)
    val results = viewModel.results.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Row(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    TextField(
                        value = prompt,
                        label = { Text(stringResource(R.string.label_prompt)) },
                        onValueChange = { prompt = it },
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(end = 16.dp)
                            .align(Alignment.CenterVertically)
                    )

                    Button(
                        onClick = {
                            viewModel.sendMessage(prompt)
                        },
                        enabled = prompt.isNotEmpty(),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = stringResource(R.string.action_go))
                    }
                }

                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                if (results.value.isNotEmpty()) {
                    LazyColumn {
                        items(results.value) { result ->
                            Column(modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()) {
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = result.role,
                                    color = if( result.role == "user") {
                                        Color.Red
                                    } else {
                                        Color.Blue
                                    }
                                )
                                Text(
                                    modifier = Modifier.padding(start = 16.dp),
                                    text = result.content
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
