package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_log.view.*
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Cpu Info Fragment.
 */
class CpuInfoFragment : BaseFragment() {

    companion object {
        const val command = "cat proc/cpuinfo"
        fun newInstance() : Fragment {
            return CpuInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common_log, container, false)
        view.logText.text = execute(command)
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_cpu_info
    }

    private fun execute(c: String) : String {

        var inputStreamReader : InputStreamReader? = null
        var bufferReader : BufferedReader? = null
        try {
            val process = Runtime.getRuntime().exec(c)
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