package jp.co.yuji.mydebugapplication.domain.usecase

import android.util.Log
import jp.co.yuji.mydebugapplication.domain.task.ExecShellTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Exec Shell UseCase.
 */
class ExecShellUseCase :BaseUseCase() {

    interface OnExecShellListener {
        fun onExecShell(result: String)
    }

    fun exec(command: String, listener: OnExecShellListener) {

        scope.launch {
            try {
                val result = ExecShellTask().exec(command)
                withContext(Dispatchers.Main) {
                    listener.onExecShell(result)
                }
            } catch(e: Exception) {
                Log.e(this::class.java.simpleName, "on catch", e)
            }

        }
    }
}