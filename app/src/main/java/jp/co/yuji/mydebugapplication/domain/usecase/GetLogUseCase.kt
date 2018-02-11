package jp.co.yuji.mydebugapplication.domain.usecase

import android.os.AsyncTask
import jp.co.yuji.mydebugapplication.domain.task.GetLogTask

/**
 * Created by yuji on 2018/01/02.
 */
class GetLogUseCase {

    interface OnGetLogListener {
        fun onGetLog(log: String)
    }

    fun getLog(listener: OnGetLogListener) {
        val task = GetLogTask (object : GetLogTask.OnGetLogListener {
            override fun onGetLog(log: String) {
                listener.onGetLog(log)
            }
        })
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}