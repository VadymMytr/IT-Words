package ua.vadymmy.it.words.domain.syncing

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.words.local.GetPredefinedKitsPreviewsUseCase
import ua.vadymmy.it.words.domain.words.local.SavePredefinedWordKitUseCase
import ua.vadymmy.it.words.domain.words.server.DownloadPredefinedWordKitsUseCase

@Reusable
class SyncPredefinedKitsUseCase @Inject constructor(
    private val getPredefinedKitsPreviewsUseCase: GetPredefinedKitsPreviewsUseCase,
    private val downloadPredefinedWordKitsUseCase: DownloadPredefinedWordKitsUseCase,
    private val savePredefinedWordKitUseCase: SavePredefinedWordKitUseCase
) : BackgroundUseCase<Unit, Unit>() {

    override suspend fun execute(request: Unit) {
        if (getPredefinedKitsPreviewsUseCase(Unit).isNotEmpty()) return
        downloadPredefinedWordKitsUseCase(Unit).forEach {
            savePredefinedWordKitUseCase(it)
        }
    }
}