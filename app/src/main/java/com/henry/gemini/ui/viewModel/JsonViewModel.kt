package com.henry.gemini.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.henry.gemini.BuildConfig
import com.henry.gemini.UiState
import com.henry.gemini.ui.model.Meme
import com.henry.gemini.ui.model.ModelName
import com.henry.gemini.ui.model.getInnerString
import com.henry.gemini.ui.model.parseMemeList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class JsonViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _results = MutableStateFlow(emptyList<Meme>())
    val results = _results.asStateFlow()

    val harassmentSafety = SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH)
    val hateSpeechSafety = SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE)

    private val generativeModel = GenerativeModel(
        modelName = ModelName.value,
        apiKey = BuildConfig.apiKey,
        // safetySettings = listOf(harassmentSafety, hateSpeechSafety)
    )

    fun sendPrompt(prompt: String) {
        val jsonFormat =
            "Generate list of memes, for this keyword: $prompt" +
                    ", in json format " +
                    "{\n" +
                    "  \"meme_list\": [\n" +
                    "    {\n" +
                    "      \"name\": " +
                    "      \"imageUrl\": " +
                    "    }\n" +
                    "  ],\n" +
                    "}"+
                    ", should be from imgflip."

        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(jsonFormat)
                _isLoading.value = false
                response.text?.let { outputContent ->
                    val innerString = getInnerString(outputContent)
                    val memeList = parseMemeList(innerString)

                    Log.d("TagGem", "outputContent: $outputContent")
                    Log.d("TagGem", "innerString: $innerString")
                    _results.value = memeList
                }
            } catch (e: Exception) {
                Log.d("Error", e.localizedMessage ?: "")
            }
        }
    }
}
