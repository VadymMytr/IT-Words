package ua.vadymmy.it.words.domain.entities.user

data class User(
    val uid: String,
    val email: String,
    val name: String = EMPTY_NAME,
    val learnProgress: Int = NO_PROGRESS
) {
    val level: UserLevel
        get() = UserLevel.fromProgress(learnProgress)

    private companion object {
        private const val EMPTY_NAME = ""
        private const val NO_PROGRESS = 0
    }
}