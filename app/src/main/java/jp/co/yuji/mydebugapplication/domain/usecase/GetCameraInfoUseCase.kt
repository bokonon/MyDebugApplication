package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.util.Log
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.domain.task.GetCameraInfoTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Get Camera Info UseCase.
 */
class GetCameraInfoUseCase: BaseUseCase() {

    interface OnGetCameraInfoListener {
        fun onGetCameraInfo(cameraList : List<CommonDto>)
    }

    fun exec(context: Context, listener: OnGetCameraInfoListener) {

        scope.launch {
            try {
                val result = GetCameraInfoTask(context).exec()
                withContext(Dispatchers.Main) {
                    listener.onGetCameraInfo(result)
                }
            } catch(e: Exception) {
                Log.e(this::class.java.simpleName, "on catch", e)
            }

        }
    }
}