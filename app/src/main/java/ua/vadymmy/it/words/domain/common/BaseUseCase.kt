package ua.vadymmy.it.words.domain.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<INPUT_TYPE, OUTPUT_TYPE> {
    protected open val executionContext: CoroutineContext = Dispatchers.Main
    protected abstract suspend fun execute(request: INPUT_TYPE): OUTPUT_TYPE

    suspend operator fun invoke(request: INPUT_TYPE): OUTPUT_TYPE {
        return withContext(executionContext) {
            execute(request)
        }
    }
}