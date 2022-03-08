package jp.co.yuji.mydebugapplication.presentation.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBroadcastReceiver(val listener: OnReceiveListener) : BroadcastReceiver() {

    interface OnReceiveListener {
        fun onReceive(intent: Intent)
    }

    override fun onReceive(context: Context, intent: Intent) {
        listener.onReceive(intent)
    }

}