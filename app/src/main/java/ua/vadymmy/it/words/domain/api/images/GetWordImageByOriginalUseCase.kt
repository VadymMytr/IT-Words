package ua.vadymmy.it.words.domain.api.images

import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.WordImage

@Reusable
class GetWordImageByOriginalUseCase @Inject constructor(
    private val imageApiRepository: ImageApiRepository
) : BackgroundUseCase<String, WordImage>() {

    override suspend fun execute(request: String): WordImage {
        return imageApiRepository.findImage(request)
    }
}