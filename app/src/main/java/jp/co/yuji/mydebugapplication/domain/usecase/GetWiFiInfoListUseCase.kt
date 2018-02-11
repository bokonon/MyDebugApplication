package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.AsyncTask
import jp.co.yuji.mydebugapplication.domain.task.GetWiFiInfoListTask

/**
 * Get WiFi ScanResult List UseCase.
 */
class GetWiFiInfoListUseCase {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>)
    }

    fun getWiFiInfoList(context: Context, listener: GetWiFiInfoListUseCase.OnGetWiFiInfoListListener) {
        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val task = GetWiFiInfoListTask (wifiManager, object : GetWiFiInfoListTask.OnGetWiFiInfoListListener {
            override fun onGetWiFiInfoList(wifiInfoList: List<ScanResult>) {
                listener.onGetWiFiInfoList(wifiInfoList)
            }
        })
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }
}