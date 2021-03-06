package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.Manifest
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.presenter.other.WiFiInfoListPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.WiFiInfoListRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_progress.view.*
import java.util.*

/**
 * WiFi Info List Fragment.
 */
class WiFiInfoListFragment : BaseFragment() {

    companion object {
        const val PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 0
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
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common_progress, container, false)

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<ScanResult>()
        adapter = WiFiInfoListRecyclerViewAdapter(list)
        view.recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        if (activity != null && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            val permissions: Array<String> = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(
                    permissions,
                    PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION)
        } else {
            addWiFiList(list)
        }

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_wifi_info_list
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            addWiFiList(adapter?.getItems())
        } else {
            progressBar?.visibility = View.GONE
        }

    }

    private fun addWiFiList(list : ArrayList<ScanResult>?) {
        if (activity == null) {
            return
        }
        presenter.getWiFiInfoList(requireActivity(), object: WiFiInfoListPresenter.OnGetWiFiInfoListListener {
            override fun onGetWiFiInfoList(wifiInfoList: List<ScanResult>) {
                list?.addAll(wifiInfoList)
                adapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE
            }
        })
    }

}