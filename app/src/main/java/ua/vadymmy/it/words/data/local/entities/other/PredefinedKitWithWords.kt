package ua.vadymmy.it.words.data.local.entities.other

import androidx.room.Embedded
import ua.vadymmy.it.words.data.local.entities.word.WordEntity
import ua.vadymmy.it.words.data.local.entities.word.kit.PredefinedWordKitEntity

data class PredefinedKitWithWords(
    @Embedded var wordKit: PredefinedWordKitEntity,
    @Embedded var word: WordEntity
)