package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.os.AsyncTask
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.domain.task.GetCameraInfoTask

/**
 * Created by yuji on 2018/01/04.
 */
class GetCameraInfoUseCase {

    interface OnGetCameraInfoListener {
        fun onGetCameraInfo(cameraList : List<CommonDto>)
    }

    fun getCameraInfo(context: Context, listener: OnGetCameraInfoListener) {
        val task = GetCameraInfoTask (context, object : GetCameraInfoTask.OnGetCameraInfoListener {
            override fun onGetCameraInfo(cameraList: List<CommonDto>) {
                listener.onGetCameraInfo(cameraList)
            }
        })
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}