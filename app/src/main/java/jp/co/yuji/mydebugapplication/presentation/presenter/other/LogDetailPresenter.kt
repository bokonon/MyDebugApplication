package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.GetLogUseCase

/**
 * Created by yuji on 2018/01/04.
 */
class LogDetailPresenter {

    interface OnGetLogListener {
        fun onGetLog(log: String)
    }

    fun getLog(listener: OnGetLogListener) {
        val useCase = GetLogUseCase()
        useCase.getLog(object : GetLogUseCase.OnGetLogListener {
            override fun onGetLog(log: String) {
                listener.onGetLog(log)
            }
        })
    }
}