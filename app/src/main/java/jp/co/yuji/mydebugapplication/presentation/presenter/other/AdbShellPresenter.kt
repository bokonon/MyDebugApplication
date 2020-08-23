package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.ExecShellUseCase

/**
 * Port Detail Presenter.
 */
class AdbShellPresenter {

    interface OnExecShellListener {
        fun onExecShell(result: String)
    }

    fun execShell(command: String, listener: OnExecShellListener) {
        val useCase = ExecShellUseCase()
        useCase.execShell(command, object : ExecShellUseCase.OnExecShellListener {
            override fun onExecShell(result: String) {
                listener.onExecShell(result)
            }
        })
    }
}