package jp.co.yuji.mydebugapplication.domain.task

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Created by yuji on 2018/01/02.
 */
class GetLogTask(private val listener: OnGetLogListener) : AsyncTask<Void, Void, String>() {

    interface OnGetLogListener {
        fun onGetLog(log: String)
    }

    override fun doInBackground(vararg params: Void?): String {
        val logText = StringBuilder()
        logText.append(getLog())
        logText.append("\n")
        logText.append("\n")
        logText.append("\n")
        logText.append("\n")
        logText.append("\n")
        logText.append("\n")
        logText.append("\n")
        return logText.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onGetLog(result)
        }
    }

    private fun getLog() : String {

//        val command = arrayOf("logcat", "-v", "time", "*:V")
//        val command = "logcat"
//        val command = arrayOf("logcat")

        val command = "logcat -d"

        val process = Runtime.getRuntime().exec(command)
        val bufferReader = BufferedReader(InputStreamReader(process.inputStream), 1024)

        return bufferReader.readText()
    }

}