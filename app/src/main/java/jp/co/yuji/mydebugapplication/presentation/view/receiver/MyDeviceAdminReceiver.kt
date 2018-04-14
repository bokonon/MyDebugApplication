package jp.co.yuji.mydebugapplication.presentation.view.receiver

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

/**
 * Device Admin Receiver.
 */
class MyDeviceAdminReceiver : DeviceAdminReceiver() {

    // adb shell dpm set-device-owner jp.co.yuji.mydebugapplication/.MyDeviceAdminReceiver

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        println("onReceive")

        when(intent.action) {
            // do something
        }
    }

    override fun onEnabled(context: Context, intent: Intent) {
        println("onEnabled")
    }

    override fun onDisabled(context: Context, intent: Intent) {
        println("onDisabled")
    }

}