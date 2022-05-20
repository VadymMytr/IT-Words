package ua.vadymmy.it.words.data.api.images

data class ImageApiEntry(
    val contentUrl: String,
    val accentColor: String
)

data class ImageApiResponse(val value: List<ImageApiEntry>)