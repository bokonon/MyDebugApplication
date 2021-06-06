package jp.co.yuji.mydebugapplication.domain.usecase

import android.util.Log
import jp.co.yuji.mydebugapplication.domain.task.GetLogTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Get Log UseCase.
 */
class GetLogUseCase: BaseUseCase() {

    interface OnGetLogListener {
        fun onGetLog(log: String)
    }

    fun exec(listener: OnGetLogListener) {
        scope.launch {
            try {
                val result = GetLogTask().exec()
                withContext(Dispatchers.Main) {
                    listener.onGetLog(result)
                }
            } catch(e: Exception) {
                Log.e(this::class.java.simpleName, "on catch", e)
            }

        }
    }
}