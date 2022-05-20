package ua.vadymmy.it.words.domain.common

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BackgroundUseCase<INPUT_TYPE, OUTPUT_TYPE> : BaseUseCase<INPUT_TYPE, OUTPUT_TYPE>() {
    override val executionContext: CoroutineContext get() = Dispatchers.IO
}