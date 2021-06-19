package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.ExecShellUseCase

/**
 * System Properties Presenter.
 */
class SystemPropertiesPresenter {

    interface OnGetSystemPropertiesListener {
        fun onGetSystemProperties(result: String)
    }

    fun getSystemProperties(listener: OnGetSystemPropertiesListener) {
        val command = "getprop"
        val useCase = ExecShellUseCase()
        useCase.exec(command, object : ExecShellUseCase.OnExecShellListener {
            override fun onExecShell(result: String) {
                listener.onGetSystemProperties(result)
            }
        })
    }
}