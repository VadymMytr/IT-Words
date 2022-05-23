package ua.vadymmy.it.words.domain.syncing

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.api.data.LocalRepository
import ua.vadymmy.it.words.domain.api.data.ServerRepository
import ua.vadymmy.it.words.domain.common.BackgroundUseCase

@Reusable
class SyncLearningWordKitsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val serverRepository: ServerRepository
) : BackgroundUseCase<Unit, Unit>() {

    override suspend fun execute(request: Unit) {
        val localLearningKits = localRepository.getLearningWordKits().toMutableList()
        val serverLearningKits = serverRepository.getLearningWordKits().toMutableList()
        if (localLearningKits.isEmpty()) {
            serverLearningKits.forEach { localRepository.addLearningWordKit(it) }
            return
        }

        val localKitsUUIDList = localLearningKits.map { localKit -> localKit.uuid }
        val onlyServerKits = serverLearningKits.filterNot { localKitsUUIDList.contains(it.uuid) }
        onlyServerKits.forEach { serverRepository.removeLearningWordKit(it) }

        val serverKitsUUIDList = serverLearningKits.map { serverKit -> serverKit.uuid }
        val (bothKits, localKits) = localLearningKits.partition { serverKitsUUIDList.contains(it.uuid) }
        bothKits.forEach { serverRepository.updateLearningWordKit(it) }
        localKits.forEach { serverRepository.addLearningWordKit(it) }
    }
}