package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_adb_shell.view.*
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Adb Shell Fragment.
 */
class AdbShellFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return AdbShellFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_adb_shell, container, false)

        val editText = view.findViewById<EditText>(R.id.adb_shell_edit_text)
        val resultText = view.findViewById<TextView>(R.id.adb_shell_result_text)

        view.adb_shell_execute_button.setOnClickListener({
            resultText.text = getExecuteText(editText.text.toString())
            view.hideKeyboard()
            resultText.requestFocus()
        })

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_adb_shell
    }

    private fun getExecuteText(command: String) : String {
        val resultText = StringBuilder()
        resultText.append(execute(command))
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        return resultText.toString()
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

    private fun View.hideKeyboard() {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}