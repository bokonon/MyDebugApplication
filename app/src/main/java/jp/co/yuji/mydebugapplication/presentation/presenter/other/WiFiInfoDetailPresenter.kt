package jp.co.yuji.mydebugapplication.presentation.presenter.other

import android.content.Context
import android.net.wifi.ScanResult
import android.os.Build
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto

/**
 * Wi-Fi Info Detail Presenter.
 */
class WiFiInfoDetailPresenter {

    fun getWiFiInfoDetail(context: Context, scanResult: ScanResult): List<CommonDto> {
        val list = ArrayList<CommonDto>()

        list.add(CommonDto("SSID", scanResult.SSID))
        list.add(CommonDto("BSSID", scanResult.BSSID))
        list.add(CommonDto("capabilities", scanResult.capabilities))
        list.add(CommonDto("level",
                context.getString(R.string.wifi_info_level_unit_text, scanResult.level)))
        list.add(CommonDto("frequency",
                context.getString(R.string.wifi_info_frequency_unit_text, scanResult.frequency)))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            list.add(CommonDto("channelWidth", getChannelWidthText(scanResult.channelWidth)))
            list.add(CommonDto("centerFreq0",
                    context.getString(R.string.wifi_info_frequency_unit_text, scanResult.centerFreq0)))
            list.add(CommonDto("centerFreq1",
                    context.getString(R.string.wifi_info_frequency_unit_text, scanResult.centerFreq1)))
            list.add(CommonDto("is80211mcResponder", scanResult.is80211mcResponder.toString()))
            list.add(CommonDto("isPasspointNetwork", scanResult.isPasspointNetwork.toString()))
            list.add(CommonDto("operatorFriendlyName", scanResult.operatorFriendlyName.toString()))
            list.add(CommonDto("venueName", scanResult.venueName.toString()))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            list.add(CommonDto("timestamp", scanResult.timestamp.toString()))
        }

        list.add(CommonDto("=== ScanResult toString ===", ""))
        val items = scanResult.toString().split(",")
        items
                .map { it.split(":") }
                .filter { it.size == 2 }
                .mapTo(list) { CommonDto(it[0], it[1]) }

        return list
    }

    private fun getChannelWidthText(channelWidth: Int): String {
        when(channelWidth) {
            ScanResult.CHANNEL_WIDTH_20MHZ -> {
                return "CHANNEL_WIDTH_20MHZ"
            }
            ScanResult.CHANNEL_WIDTH_40MHZ -> {
                return "CHANNEL_WIDTH_40MHZ"
            }
            ScanResult.CHANNEL_WIDTH_80MHZ -> {
                return "CHANNEL_WIDTH_80MHZ"
            }
            ScanResult.CHANNEL_WIDTH_160MHZ -> {
                return "CHANNEL_WIDTH_160MHZ"
            }
            ScanResult.CHANNEL_WIDTH_80MHZ_PLUS_MHZ -> {
                return "CHANNEL_WIDTH_80MHZ_PLUS_MHZ"
            }
        }
        return "Unspecified"
    }

}