package ua.vadymmy.it.words.domain.models.word.common

import android.graphics.Color

data class WordImage constructor(val url: String, val colorHex: String) {

    val color get() = Color.parseColor(colorHex)
    var isNotEmpty = url.isNotEmpty()

    companion object {
        private const val EMPTY_URL = ""
        private const val EMPTY_COLOR = "#ffffff"
        val empty get() = WordImage(EMPTY_URL, EMPTY_COLOR)
    }
}