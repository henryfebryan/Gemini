package com.henry.gemini.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.henry.gemini.R
import com.henry.gemini.UiState
import com.henry.gemini.ui.viewModel.TextPromptViewModel

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextPromptScreen(
    navController: NavHostController,
    viewModel: TextPromptViewModel = viewModel()
) {
    val placeholderResult = stringResource(R.string.results_placeholder)
    var prompt by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Text Prompt") },
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
                        onClick = { viewModel.sendPrompt(prompt) },
                        enabled = prompt.isNotEmpty(),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text(text = stringResource(R.string.action_go))
                    }
                }

                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    var textColor = MaterialTheme.colorScheme.onSurface
                    if (uiState is UiState.Error) {
                        textColor = MaterialTheme.colorScheme.error
                        result = (uiState as UiState.Error).errorMessage
                    } else if (uiState is UiState.Success) {
                        textColor = MaterialTheme.colorScheme.onSurface
                        result = (uiState as UiState.Success).outputText
                    }
                    val scrollState = rememberScrollState()
                    Text(
                        text = result,
                        textAlign = TextAlign.Start,
                        color = textColor,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    )
                }
            }
        }
    )
}
