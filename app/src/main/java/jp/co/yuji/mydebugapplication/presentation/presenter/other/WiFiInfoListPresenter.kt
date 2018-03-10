package jp.co.yuji.mydebugapplication.presentation.presenter.other

import android.content.Context
import android.net.wifi.ScanResult
import jp.co.yuji.mydebugapplication.domain.usecase.GetWiFiInfoListUseCase

/**
 * WiFi Info List Presenter.
 */
class WiFiInfoListPresenter {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>)
    }

    fun getWiFiInfoList(context: Context, listener: OnGetWiFiInfoListListener) {
        val useCase = GetWiFiInfoListUseCase()
        useCase.getWiFiInfoList(context, object : GetWiFiInfoListUseCase.OnGetWiFiInfoListListener {
            override fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>) {
                listener.onGetWiFiInfoList(wifiInfoList)
            }
        })
    }

}