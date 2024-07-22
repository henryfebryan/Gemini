package com.henry.gemini.ui.screen

/**
 *
 * @author Henry
 * Created on 22/07/2024.
 */
enum class Screen {
    HOME,
    TEXT_PROMPT,
    IMAGE_PROMPT,
    JSON,
    CHAT,
}
sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object TextPrompt : NavigationItem(Screen.TEXT_PROMPT.name)
    object ImagePrompt : NavigationItem(Screen.IMAGE_PROMPT.name)
    object Json : NavigationItem(Screen.JSON.name)
    object Chat : NavigationItem(Screen.CHAT.name)
}
