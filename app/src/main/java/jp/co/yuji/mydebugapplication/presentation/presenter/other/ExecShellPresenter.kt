package jp.co.yuji.mydebugapplication.presentation.presenter.other

import jp.co.yuji.mydebugapplication.domain.usecase.ExecShellUseCase

/**
 * Exec Shell Presenter.
 */
class ExecShellPresenter {

    interface OnExecShellListener {
        fun onExecShell(result: String)
    }

    fun execShell(command: String, listener: OnExecShellListener) {
        val useCase = ExecShellUseCase()
        useCase.exec(command, object : ExecShellUseCase.OnExecShellListener {
            override fun onExecShell(result: String) {
                listener.onExecShell(result)
            }
        })
    }
}