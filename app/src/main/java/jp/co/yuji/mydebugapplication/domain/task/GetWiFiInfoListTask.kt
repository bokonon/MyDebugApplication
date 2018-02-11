package jp.co.yuji.mydebugapplication.domain.task

import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.AsyncTask

/**
 * Get WiFi ScanResult List Task.
 */
class GetWiFiInfoListTask(private val wifiManager: WifiManager, private val listener: OnGetWiFiInfoListListener) : AsyncTask<Void, Void, List<ScanResult>>() {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList: List<ScanResult>)
    }

    override fun doInBackground(vararg params: Void?): List<ScanResult> {
        return getWiFiInfoList()
    }

    override fun onPostExecute(scanResultList: List<ScanResult>) {
        super.onPostExecute(scanResultList)
        listener.onGetWiFiInfoList(scanResultList)
    }

    private fun getWiFiInfoList(): List<ScanResult> {
        wifiManager.startScan()
        return wifiManager.scanResults
    }
}