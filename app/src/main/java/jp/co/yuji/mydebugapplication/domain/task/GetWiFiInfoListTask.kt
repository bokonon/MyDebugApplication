package jp.co.yuji.mydebugapplication.domain.task

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager

/**
 * Get WiFi ScanResult List Task.
 */
class GetWiFiInfoListTask {

    suspend fun exec(wifiManager: WifiManager): List<ScanResult> {
        wifiManager.startScan()
        return wifiManager.scanResults
    }
}