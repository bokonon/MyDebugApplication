package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.other.LogDetailPresenter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_log_progress.view.*

/**
 * Log Detail Fragment.
 */
class LogDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return LogDetailFragment()
        }
    }

    private val presenter = LogDetailPresenter()

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common_log_progress, container, false)

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        presenter.getLog(object : LogDetailPresenter.OnGetLogListener {
            override fun onGetLog(log: String) {
                view.logText.text = log
                progressBar?.visibility = View.GONE
            }
        })

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_log_detail
    }

}