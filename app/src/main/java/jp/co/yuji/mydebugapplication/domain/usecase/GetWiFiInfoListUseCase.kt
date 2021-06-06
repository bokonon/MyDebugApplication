package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import jp.co.yuji.mydebugapplication.domain.task.GetWiFiInfoListTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Get WiFi ScanResult List UseCase.
 */
class GetWiFiInfoListUseCase: BaseUseCase() {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>)
    }

    fun exec(context: Context, listener: GetWiFiInfoListUseCase.OnGetWiFiInfoListListener) {
        scope.launch {
            try {
                val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val result = GetWiFiInfoListTask().exec(wifiManager)
                withContext(Dispatchers.Main) {
                    listener.onGetWiFiInfoList(result)
                }
            } catch(e: Exception) {
                Log.e(this::class.java.simpleName, "on catch", e)
            }

        }
    }
}