package jp.co.yuji.mydebugapplication.presentation.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import jp.co.yuji.mydebugapplication.domain.model.MyBatteryInfo


class BatteryReceiver(val listener : BatteryReceiver.OnBatteryChangeListener) : BroadcastReceiver() {

    companion object {
        const val DEFAULT_VALUE = -1
    }

    interface OnBatteryChangeListener {
        fun onBatteryChange(myBatteryInfo : MyBatteryInfo)
    }

    override fun onReceive(context: Context, intent: Intent) {

        when(intent.action) {
            Intent.ACTION_BATTERY_CHANGED -> {
                println("battery change")

                val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, DEFAULT_VALUE)
                val iconScale = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, DEFAULT_VALUE)
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, DEFAULT_VALUE)
                val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, DEFAULT_VALUE)
                val present = intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, DEFAULT_VALUE)
                val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, DEFAULT_VALUE)
                var technology = "unknown"
                val rawTechnology: String? = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
                if (rawTechnology != null) {
                    technology = rawTechnology
                }
                val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, DEFAULT_VALUE)
                val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, DEFAULT_VALUE)

                listener.onBatteryChange(
                        MyBatteryInfo(
                                health,
                                iconScale,
                                level,
                                plugged,
                                present,
                                scale,
                                status,
                                technology,
                                temperature,
                                voltage))
            }
            else -> {
                // NOP
            }
        }
    }
}