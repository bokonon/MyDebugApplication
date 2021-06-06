package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.GetLogUseCase

/**
 * Log Detail Presenter.
 */
class LogDetailPresenter {

    interface OnGetLogListener {
        fun onGetLog(log: String)
    }

    fun getLog(listener: OnGetLogListener) {
        val useCase = GetLogUseCase()
        useCase.exec(object : GetLogUseCase.OnGetLogListener {
            override fun onGetLog(log: String) {
                listener.onGetLog(log)
            }
        })
    }
}