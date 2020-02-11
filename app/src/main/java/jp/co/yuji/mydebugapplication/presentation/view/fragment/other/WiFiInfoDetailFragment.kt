package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.content.Context
import android.net.wifi.ScanResult
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.presenter.other.WiFiInfoDetailPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.WiFiInfoDetailRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common.view.*

/**
 * WiFi Info Detail Fragment.
 */
class WiFiInfoDetailFragment : BaseFragment() {

    companion object {

        const val ARG_KEY = "arg_key"

        fun newInstance(scanResult : ScanResult) : Fragment {
            val fragment = WiFiInfoDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_KEY, scanResult)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val presenter = WiFiInfoDetailPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val scanResult = arguments?.getParcelable<ScanResult>(ARG_KEY)

        if (activity != null && scanResult != null) {
            val adapter = WiFiInfoDetailRecyclerViewAdapter(activity!!, getWiFiInfoDetail(activity!!, scanResult))
            view.recyclerView.adapter = adapter
        }

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_wifi_info_detail
    }

    private fun getWiFiInfoDetail(context: Context, scanResult: ScanResult): List<CommonDto> {
        return presenter.getWiFiInfoDetail(context, scanResult)
    }

}