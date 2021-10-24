package jp.co.yuji.mydebugapplication.domain.usecase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log

/**
 * Get WiFi ScanResult List UseCase.
 */
class GetWiFiInfoListUseCase: BaseUseCase() {

    interface OnGetWiFiInfoListListener {
        fun onGetWiFiInfoList(wifiInfoList : List<ScanResult>)
    }

    fun exec(context: Context, listener: GetWiFiInfoListUseCase.OnGetWiFiInfoListListener) {
        val applicationContext = context.applicationContext ?: return
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(this::class.java.simpleName, "onReceive: $intent")
                val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
                if (success) {
                    scanSuccess(wifiManager, listener)
                } else {
                    scanFailure(wifiManager, listener)
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        Log.d(this::class.java.simpleName, "success: $success")
        if (!success) {
            // scan failure handling
            scanFailure(wifiManager, listener)
        }
    }

    private fun scanSuccess(wifiManager: WifiManager, listener: GetWiFiInfoListUseCase.OnGetWiFiInfoListListener) {
        val results =  wifiManager.scanResults
        Log.d(this::class.java.simpleName, "result: $results")
        listener.onGetWiFiInfoList(results)
    }

    private fun scanFailure(wifiManager: WifiManager, listener: GetWiFiInfoListUseCase.OnGetWiFiInfoListListener) {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults
        Log.d(this::class.java.simpleName, "result: $results")
        listener.onGetWiFiInfoList(results)
    }
}