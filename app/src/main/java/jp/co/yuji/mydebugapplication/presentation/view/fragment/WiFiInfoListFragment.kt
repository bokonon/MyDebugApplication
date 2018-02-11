package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.net.wifi.ScanResult
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.WiFiInfoListPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.WiFiInfoListRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_app_list.view.*
import java.util.*

/**
 * WiFi Info List Fragment.
 */
class WiFiInfoListFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return WiFiInfoListFragment()
        }
    }

    private val presenter = WiFiInfoListPresenter()

    private var adapter: WiFiInfoListRecyclerViewAdapter? = null

    private var progressBar: ProgressBar? = null

    private val listener = object: WiFiInfoListRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(scanResult: ScanResult) {
            val fragment = WiFiInfoDetailFragment.newInstance(scanResult)
            activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_wifi_info_list, container, false)

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<ScanResult>()
        adapter = WiFiInfoListRecyclerViewAdapter(list)
        view.recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        addWiFiList(list)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_wifi_info_list
    }

    private fun addWiFiList(list : ArrayList<ScanResult>) {
        presenter.getWiFiInfoList(activity, object: WiFiInfoListPresenter.OnGetWiFiInfoListListener {
            override fun onGetWiFiInfoList(wifiInfoList: List<ScanResult>) {
                list.addAll(wifiInfoList)
                adapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE
            }
        })
    }

}