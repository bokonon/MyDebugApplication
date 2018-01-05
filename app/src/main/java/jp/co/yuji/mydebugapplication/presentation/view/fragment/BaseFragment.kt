package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by yuji on 2018/01/04.
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setTitle(getTitle())
        return null
    }

    abstract fun getTitle(): Int

    private fun setTitle(stringRes: Int) {
        activity.title = activity.getString(stringRes)
    }
}