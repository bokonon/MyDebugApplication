package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.other.AdbShellPresenter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_adb_shell.view.*

/**
 * Adb Shell Fragment.
 */
class AdbShellFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return AdbShellFragment()
        }
    }

    private val presenter = AdbShellPresenter()

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_adb_shell, container, false)

        view.adbShellResultText.text = "pm list features\n" +
                "pm list libraries\n" +
                "ls /system/bin\n" +
                "etc"

        view.adbShellExecuteButton.setOnClickListener {
            view.adbShellResultText.text = getExecuteText(view.adbShellEditText.text.toString())
            view.hideKeyboard()
            view.adbShellResultText.requestFocus()

            progressBar = view.progressBar
            progressBar?.visibility = View.VISIBLE

            val command = view.adbShellEditText.text.toString()
            presenter.execShell(command, object : AdbShellPresenter.OnExecShellListener {
                override fun onExecShell(result: String) {
                    view.adbShellResultText.text = getExecuteText(result)
                    view.hideKeyboard()
                    view.adbShellResultText.requestFocus()
                    progressBar?.visibility = View.GONE
                }
            })
        }

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_adb_shell
    }

    private fun getExecuteText(result: String) : String {
        val resultText = StringBuilder()
        resultText.append(result)
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        resultText.append("\n")
        return resultText.toString()
    }

    private fun View.hideKeyboard() {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

}