package jp.co.yuji.mydebugapplication.presentation.presenter

import android.content.Context
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.domain.usecase.GetCameraInfoUseCase

/**
 * Created by yuji on 2018/01/04.
 */
class CameraInfoPresenter {

    interface OnGetCameraInfoListener {
        fun onGetCameraInfo(cameraList : List<CommonDto>)
    }

    fun getCameraInfo(context: Context, listener: OnGetCameraInfoListener) {
        val useCase = GetCameraInfoUseCase()
        useCase.getCameraInfo(context, object : GetCameraInfoUseCase.OnGetCameraInfoListener {
            override fun onGetCameraInfo(cameraList: List<CommonDto>) {
                listener.onGetCameraInfo(cameraList)
            }
        })
    }

}