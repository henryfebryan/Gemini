package com.henry.gemini.ui.model

import org.json.JSONObject

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
data class Meme(val name: String, val imageUrl: String)

fun parseMemeList(jsonString: String): List<Meme> {
    val jsonObject = JSONObject(jsonString)  // Parse JSON string
    val memeList = jsonObject.getJSONArray("meme_list")  // Get the meme_list array
    val memes = mutableListOf<Meme>()

    for (i in 0 until memeList.length()) {
        val memeJsonObject = memeList.getJSONObject(i)  // Get each meme object
        val name = memeJsonObject.getString("name")
        val imageUrl = memeJsonObject.getString("imageUrl")
        memes.add(Meme(name, imageUrl))
    }

    return memes.toList()
}

fun getInnerString(text: String): String {
    val startIndex = text.indexOf("```json")
    if (startIndex == -1) {
        return ""  // No starting delimiter found
    }

    val endIndex = text.indexOf("```", startIndex + 7)  // Search after starting delimiter
    if (endIndex == -1) {
        return ""  // No ending delimiter found
    }

    return text.substring(startIndex + 7, endIndex)  // Extract substring between delimiters
}
