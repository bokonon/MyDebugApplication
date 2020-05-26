package jp.co.yuji.mydebugapplication.domain.usecase

import android.os.AsyncTask
import jp.co.yuji.mydebugapplication.domain.task.ExecShellTask

/**
 * Exec Shell UseCase.
 */
class ExecShellUseCase {

    interface OnExecShellListener {
        fun onExecShell(result: String)
    }

    fun execShell(command: String, listener: OnExecShellListener) {
        val task = ExecShellTask (object : ExecShellTask.OnExecShellListener {
            override fun onExecShell(result: String) {
                listener.onExecShell(result)
            }
        })
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, command)
    }
}