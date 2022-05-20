package ua.vadymmy.it.words.domain.api.images

import dagger.Reusable
import javax.inject.Inject
import ua.vadymmy.it.words.domain.common.BackgroundUseCase
import ua.vadymmy.it.words.domain.entities.WordImage

@Reusable
class GetWordImageByOriginalUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) : BackgroundUseCase<String, WordImage>() {

    override suspend fun execute(request: String): WordImage {
        return imageRepository.findImage(request)
    }
}