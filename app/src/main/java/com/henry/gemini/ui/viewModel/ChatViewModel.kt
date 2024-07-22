package com.henry.gemini.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.henry.gemini.BuildConfig
import com.henry.gemini.ui.model.Message
import com.henry.gemini.ui.model.ModelName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
class ChatViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _results = MutableStateFlow(emptyList<Message>())
    val results = _results.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = ModelName.value,
        apiKey = BuildConfig.apiKey
    )

    private val chat = generativeModel.startChat(
//        history = listOf(
//            content(role = "user") { text("Hello, I want to ask something.") },
//            content(role = "model") { text("Great to meet you. What would you like to know?") }
//        )
    )

    fun sendMessage(message: String) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                chat.sendMessage(message)
                _isLoading.value = false
                _results.value = chat.history.map { history ->
                    Message(
                        content = history.parts.first().asTextOrNull().orEmpty(),
                        role = history.role.orEmpty()
                    )
                }.reversed()
            } catch (e: Exception) {
                Log.d("Error", e.localizedMessage ?: "")
            }
        }
    }

    fun sendMessageStream(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                chat.sendMessageStream(message).collect { chunk ->
                    print(chunk.text)
                }
            } catch (e: Exception) {
                Log.d("Error", e.localizedMessage ?: "")
            }
        }
    }
}
