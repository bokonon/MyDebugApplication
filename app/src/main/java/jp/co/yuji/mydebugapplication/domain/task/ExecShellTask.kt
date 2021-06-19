package jp.co.yuji.mydebugapplication.domain.task

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Exec Shell Task.
 */
class ExecShellTask {

    suspend fun exec(vararg params: String?): String {
        val command: String = params[0] ?: return ""
        return getPort(command)
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