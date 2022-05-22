package ua.vadymmy.it.words.domain.words.both

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.word.common.Word
import ua.vadymmy.it.words.domain.entities.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.words.both.AddLearningWordToKitUseCase.AddWordQuery

@Reusable
class AddLearningWordToKitUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<AddWordQuery, Unit>() {

    override suspend fun execute(request: AddWordQuery) {
        localRepository.addLearningWordToKit(request.kit.uuid, request.word)

        request.kit.words.add(request.word)
        serverRepository.updateLearningWordKit(request.kit)
    }

    data class AddWordQuery(val kit: LearningWordKit, val word: Word)
}