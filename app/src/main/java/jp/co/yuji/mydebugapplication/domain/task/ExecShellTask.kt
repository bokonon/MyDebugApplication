package jp.co.yuji.mydebugapplication.domain.task

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Exec Shell Task.
 */
class ExecShellTask(private val listener: OnExecShellListener) : AsyncTask<String, Void, String>() {

    interface OnExecShellListener {
        fun onExecShell(result: String)
    }

    override fun doInBackground(vararg params: String?): String {
        val command: String = params[0] ?: return ""
        return getPort(command)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onExecShell(result)
        }
    }

    private fun getPort(command: String) : String {

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
                inputStreamReader?.close()
                bufferReader?.close()
            }
            catch (e: Exception) {
                return e.message.toString()
            }

        }

    }
}