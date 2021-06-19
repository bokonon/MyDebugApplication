package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.other.SystemPropertiesPresenter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_log_progress.view.*

/**
 * System Properties Fragment.
 */
class SystemPropertiesFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return SystemPropertiesFragment()
        }
    }

    private val presenter = SystemPropertiesPresenter()

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common_log_progress, container, false)
        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        presenter.getSystemProperties(object : SystemPropertiesPresenter.OnGetSystemPropertiesListener {
            override fun onGetSystemProperties(result: String) {
                view.logText.text = getSystemPropertiesText(result)
                progressBar?.visibility = View.GONE
            }
        })

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_system_properties
    }

    private fun getSystemPropertiesText(result: String) : String {
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
}