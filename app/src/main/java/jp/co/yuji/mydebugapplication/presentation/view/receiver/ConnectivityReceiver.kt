package jp.co.yuji.mydebugapplication.presentation.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo


class ConnectivityReceiver(val listener : OnConnectivityListener) : BroadcastReceiver() {

    interface OnConnectivityListener {
        fun onConnectivityReceive(networkInfo : NetworkInfo?)
    }

    override fun onReceive(context: Context, intent: Intent) {

        when(intent.action) {
            ConnectivityManager.CONNECTIVITY_ACTION -> {
                val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = manager.activeNetworkInfo
                if (networkInfo != null) {
                    println(networkInfo.toString())
                }
                listener.onConnectivityReceive(networkInfo)
            }
            else -> {
                // NOP
            }
        }
    }

}