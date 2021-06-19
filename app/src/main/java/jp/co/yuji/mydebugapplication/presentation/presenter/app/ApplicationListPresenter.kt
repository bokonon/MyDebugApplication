package jp.co.yuji.mydebugapplication.presentation.presenter.app

import android.content.Context
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.domain.usecase.GetAppListUseCase
import jp.co.yuji.mydebugapplication.presentation.view.fragment.app.ApplicationInfoFragment

/**
 * Application List Presenter.
 */
class ApplicationListPresenter {

    interface OnGetApplicationListListener {
        fun onGetApplicationList(appList : List<ApplicationListDto>)
    }

    fun getApplicationList(context: Context, actionType: ApplicationInfoFragment.ActionType, listener: OnGetApplicationListListener) {
        val useCase = GetAppListUseCase()
        useCase.exec(context, actionType, object : GetAppListUseCase.OnGetApplicationListListener {
            override fun onGetApplicationList(appList: List<ApplicationListDto>) {
                listener.onGetApplicationList(appList)
            }
        })
    }
}