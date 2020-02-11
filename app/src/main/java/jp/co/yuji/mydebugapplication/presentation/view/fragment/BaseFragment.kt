package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.presentation.view.activity.BaseActivity

/**
 * Fragment Base Class.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setTitle(getTitle())
        return null
    }

    abstract fun getTitle(): Int

    private fun setTitle(stringRes: Int) {
        if (activity != null) {
            activity!!.title = activity!!.getString(stringRes)
        }
    }

    fun setTitleLazy(stringRes: Int) {
        if (activity != null) {
            activity!!.title = activity!!.getString(stringRes)
        }
    }

    fun postLogEvent(contentType: String) {
        val baseActivity = activity
        if (baseActivity is BaseActivity) {
            baseActivity.postLogEvent(contentType)
        }
    }

}