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

        var inputStreamReader : InputStreamReader? = null
        var bufferReader : BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec(command)
            inputStreamReader = InputStreamReader(process.inputStream)
            bufferReader = BufferedReader(inputStreamReader, 1024)

            return bufferReader.readText()
        }
        catch (e: Exception) {
            return e.message.toString()
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close()
                }
                if (bufferReader != null) {
                    bufferReader.close()
                }
            }
            catch (e: Exception) {
                return e.message.toString()
            }

        }
    }

}