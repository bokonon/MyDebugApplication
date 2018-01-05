package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.LogDetailPresenter
import kotlinx.android.synthetic.main.fragment_log_detail.view.*

/**
 * Created by yuji on 2018/01/04.
 */
class LogDetailFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return LogDetailFragment()
        }
    }

    private val presenter = LogDetailPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_log_detail, container, false)
        view.log_text.movementMethod = ScrollingMovementMethod.getInstance()

        presenter.getLog(object : LogDetailPresenter.OnGetLogListener {
            override fun onGetLog(log: String) {
                view.log_text.text = log
            }
        })

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_log_detail
    }

}