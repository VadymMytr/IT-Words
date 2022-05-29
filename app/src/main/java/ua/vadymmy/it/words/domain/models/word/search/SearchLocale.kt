package ua.vadymmy.it.words.domain.models.word.search

enum class SearchLocale(private val ranges: Array<IntRange>) {
    UK(
        arrayOf(
            IntRange(1040, 1103)
        )
    ),
    EN(
        arrayOf(
            IntRange(65, 90),
            IntRange(97, 122)
        )
    );

    val randomLetterCode: Int
        get() {
            val randomRange = ranges.random()
            return randomRange.random()
        }

    private fun isQueryAllowed(query: String) = query.any {
        isLetterAllowed(it)
    }

    private fun isLetterAllowed(letter: Char) = ranges.any { range ->
        range.contains(letter.code)
    }

    companion object {
        fun recognize(query: String) = values().firstOrNull { locale ->
            locale.isQueryAllowed(query)
        } ?: EN
    }
}