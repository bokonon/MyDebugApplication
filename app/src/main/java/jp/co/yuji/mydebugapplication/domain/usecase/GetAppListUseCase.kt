package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.os.AsyncTask
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.domain.task.GetAppListTask
import jp.co.yuji.mydebugapplication.presentation.view.fragment.ApplicationInfoFragment

/**
 * Created by yuji on 2018/01/04.
 */
class GetAppListUseCase {

    interface OnGetApplicationListListener {
        fun onGetApplicationList(appList : List<ApplicationListDto>)
    }

    fun getApplicationList(context: Context, actionType: ApplicationInfoFragment.ActionType, listener: OnGetApplicationListListener) {
        val task = GetAppListTask (context, actionType, object : GetAppListTask.OnGetApplicationListListener {
            override fun onGetApplicationList(appList: List<ApplicationListDto>) {
                listener.onGetApplicationList(appList)
            }
        })
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}