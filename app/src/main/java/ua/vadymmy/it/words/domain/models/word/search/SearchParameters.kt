package ua.vadymmy.it.words.domain.models.word.search

data class SearchParameters(val query: String) {
    val locale get() = SearchLocale.recognize(query)
    val startAt get() = query
    val endBefore get() = Char(query.first().code.inc()).toString()
}