package jp.co.yuji.mydebugapplication.presentation.presenter

import android.content.Context
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.domain.usecase.GetAppListUseCase
import jp.co.yuji.mydebugapplication.presentation.view.fragment.ApplicationInfoFragment

/**
 * Created by yuji on 2018/01/04.
 */
class ApplicationListPresenter {

    interface OnGetApplicationListListener {
        fun onGetApplicationList(appList : List<ApplicationListDto>)
    }

    fun getApplicationList(context: Context, actionType: ApplicationInfoFragment.ActionType, listener: OnGetApplicationListListener) {
        val useCase = GetAppListUseCase()
        useCase.getApplicationList(context, actionType, object : GetAppListUseCase.OnGetApplicationListListener {
            override fun onGetApplicationList(appList: List<ApplicationListDto>) {
                listener.onGetApplicationList(appList)
            }
        })
    }
}