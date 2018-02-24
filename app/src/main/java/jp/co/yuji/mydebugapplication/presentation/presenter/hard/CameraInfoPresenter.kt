package jp.co.yuji.mydebugapplication.presentation.presenter.hard

import android.content.Context
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.domain.usecase.GetCameraInfoUseCase

/**
 * Camera Info Presenter.
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