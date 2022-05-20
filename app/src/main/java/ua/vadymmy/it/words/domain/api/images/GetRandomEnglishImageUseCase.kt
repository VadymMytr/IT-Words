package ua.vadymmy.it.words.domain.api.images

import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.WordImage

@Reusable
class GetRandomEnglishImageUseCase @Inject constructor(
    private val imageApiRepository: ImageApiRepository
) : BackgroundUseCase<Unit, WordImage>() {

    private companion object {
        private const val ENGLISH_QUERY = "English"
        private const val ENGLISH_ITEMS_COUNT = 100
    }

    override suspend fun execute(request: Unit): WordImage {
        return imageApiRepository.findImages(ENGLISH_QUERY, ENGLISH_ITEMS_COUNT).random()
    }
}