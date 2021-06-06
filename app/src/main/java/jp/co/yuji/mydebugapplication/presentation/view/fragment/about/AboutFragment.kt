package jp.co.yuji.mydebugapplication.presentation.view.fragment.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.view.*

class AboutFragment : BaseFragment() {

    companion object {

        const val ARG_KEY = "arg_key"

        fun newInstance(url : String) : Fragment {
            val fragment = AboutFragment()
            val bundle = Bundle()
            bundle.putString(ARG_KEY, url)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        arguments?.getString(ARG_KEY).let {
            view.webView.loadUrl(it!!)
        }
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_about
    }

}