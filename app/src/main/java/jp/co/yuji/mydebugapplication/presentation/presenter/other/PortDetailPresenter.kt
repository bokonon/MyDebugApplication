package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.ExecShellUseCase

/**
 * Port Detail Presenter.
 */
class PortDetailPresenter {

    interface OnGetPortListener {
        fun onGetPort(result: String)
    }

    fun getPort(command: String, listener: OnGetPortListener) {
        val useCase = ExecShellUseCase()
        useCase.exec(command, object : ExecShellUseCase.OnExecShellListener {
            override fun onExecShell(result: String) {
                listener.onGetPort(result)
            }
        })
    }
}