package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.other.PortDetailPresenter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.about.AboutFragment
import kotlinx.android.synthetic.main.fragment_common_log_progress.view.*

class PortDetailFragment: BaseFragment() {

    companion object {
        const val ARG_KEY = "arg_key"
        fun newInstance(command : String) : Fragment {
            val fragment = PortDetailFragment()
            val bundle = Bundle()
            bundle.putString(ARG_KEY, command)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val presenter = PortDetailPresenter()

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common_log_progress, container, false)
        val command = arguments?.getString(AboutFragment.ARG_KEY)
        if (command != null) {
            progressBar = view.progressBar
            progressBar?.visibility = View.VISIBLE

            presenter.getPort(command, object : PortDetailPresenter.OnGetPortListener {
                override fun onGetPort(result: String) {
                    view.logText.text = getPortText(result)
                    progressBar?.visibility = View.GONE
                }
            })
        }

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_port_detail
    }

    private fun getPortText(result: String) : String {
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