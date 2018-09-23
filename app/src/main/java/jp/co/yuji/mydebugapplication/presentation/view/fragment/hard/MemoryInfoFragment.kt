package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import java.io.BufferedReader
import java.io.InputStreamReader

class MemoryInfoFragment : BaseFragment() {

    companion object {
        const val command = "cat proc/meminfo"
        fun newInstance() : Fragment {
            return MemoryInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_memory_info, container, false)
        val resultText = view.findViewById<TextView>(R.id.memory_info_text)
        resultText.text = execute(MemoryInfoFragment.command)
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_memory_info
    }

    private fun execute(command: String) : String {

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