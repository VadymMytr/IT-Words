package ua.vadymmy.it.words.domain.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<INPUT_TYPE, OUTPUT_TYPE> {
    private val mainContext: CoroutineContext = Dispatchers.Main
    protected open val executionContext: CoroutineContext = mainContext
    protected abstract suspend fun execute(request: INPUT_TYPE): OUTPUT_TYPE

    suspend operator fun invoke(request: INPUT_TYPE): OUTPUT_TYPE {
        return withContext(executionContext) {
            val result = execute(request)
            withContext(mainContext) withMainContext@{
                return@withMainContext result
            }
        }
    }
}