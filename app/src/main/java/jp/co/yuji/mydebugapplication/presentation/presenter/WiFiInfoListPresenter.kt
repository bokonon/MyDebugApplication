package jp.co.yuji.mydebugapplication.presentation.presenter

import android.content.Context
import android.net.wifi.ScanResult
import jp.co.yuji.mydebugapplication.domain.usecase.GetWiFiInfoListUseCase

/**
 * Created by yuji on 2018/02/10.
 */
class WiFiInfoListPresenter {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>)
    }

    fun getWiFiInfoList(context: Context, listener: WiFiInfoListPresenter.OnGetWiFiInfoListListener) {
        val useCase = GetWiFiInfoListUseCase()
        useCase.getWiFiInfoList(context, object : GetWiFiInfoListUseCase.OnGetWiFiInfoListListener {
            override fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>) {
                listener.onGetWiFiInfoList(wifiInfoList)
            }
        })
    }

}