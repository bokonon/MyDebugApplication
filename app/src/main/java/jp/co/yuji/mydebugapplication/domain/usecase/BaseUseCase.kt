package jp.co.yuji.mydebugapplication.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren

abstract class BaseUseCase {

    val scope = CoroutineScope(Dispatchers.Default)

    fun cancel() {
        scope.coroutineContext.cancelChildren()
    }
}