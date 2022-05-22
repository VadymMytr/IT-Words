package ua.vadymmy.it.words.domain.words.both

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.models.word.kit.LearningWordKit
import ua.vadymmy.it.words.domain.words.both.DeleteLearningWordFromKitUseCase.DeleteWordQuery

@Reusable
class DeleteLearningWordFromKitUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<DeleteWordQuery, Unit>() {

    override suspend fun execute(request: DeleteWordQuery) {
        val wordToRemove = request.kit.words[request.deleteAt]
        localRepository.deleteLearningWordFromKit(request.kit.uuid, wordToRemove)

        request.kit.words.remove(wordToRemove)
        serverRepository.updateLearningWordKit(request.kit)
    }

    data class DeleteWordQuery(val kit: LearningWordKit, val deleteAt: Int)
}