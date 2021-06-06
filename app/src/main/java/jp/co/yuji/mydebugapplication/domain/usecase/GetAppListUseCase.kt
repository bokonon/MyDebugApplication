package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.util.Log
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.domain.task.GetAppListTask
import jp.co.yuji.mydebugapplication.presentation.view.fragment.app.ApplicationInfoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by yuji on 2018/01/04.
 */
class GetAppListUseCase: BaseUseCase() {

    interface OnGetApplicationListListener {
        fun onGetApplicationList(appList : List<ApplicationListDto>)
    }

    fun exec(context: Context, actionType: ApplicationInfoFragment.ActionType, listener: OnGetApplicationListListener) {
        val packageManager = context.packageManager
        scope.launch {
            try {
                val result = GetAppListTask(packageManager).exec(actionType)
                withContext(Dispatchers.Main) {
                    listener.onGetApplicationList(result)
                }
            } catch(e: Exception) {
                Log.e(this::class.java.simpleName, "on catch", e)
            }

        }
    }
}