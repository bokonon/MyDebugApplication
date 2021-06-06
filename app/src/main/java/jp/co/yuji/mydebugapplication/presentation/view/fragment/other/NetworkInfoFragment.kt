package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import jp.co.yuji.mydebugapplication.presentation.view.receiver.ConnectivityReceiver
import kotlinx.android.synthetic.main.fragment_common.view.*

/**
 * Network Info Fragment
 */
class NetworkInfoFragment : BaseFragment() {

    companion object {
        fun newInstance(): Fragment {
            return NetworkInfoFragment()
        }
    }

    private lateinit var adapter : CommonRecyclerViewAdapter

    private val receiver = ConnectivityReceiver(object : ConnectivityReceiver.OnConnectivityListener {
        override fun onConnectivityReceive(networkInfo: NetworkInfo?) {
            updateView(networkInfo)
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)

        if (activity != null) {
            adapter = CommonRecyclerViewAdapter(requireActivity(), ArrayList())
            view.recyclerView.adapter = adapter
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(receiver)
    }

    override fun getTitle(): Int {
        return R.string.screen_name_network_info
    }

    private fun updateView(networkInfo: NetworkInfo?) {
        val list = adapter.items
        list.clear()

        if (networkInfo != null) {
            list.add(CommonDto("state", networkInfo.state?.toString().orEmpty()))
            list.add(CommonDto("detailedState", networkInfo.detailedState?.toString().orEmpty()))
            list.add(CommonDto("reason", networkInfo.reason?.toString().orEmpty()))
            list.add(CommonDto("extraInfo", networkInfo.extraInfo?.toString().orEmpty()))
            list.add(CommonDto("type", networkInfo.type.toString()))
            list.add(CommonDto("typeName", networkInfo.typeName?.toString().orEmpty()))
            list.add(CommonDto("subtype", networkInfo.subtype.toString()))
            list.add(CommonDto("subtypeName", networkInfo.subtypeName?.toString().orEmpty()))
        } else {
            list.add(CommonDto("networkInfo", "none"))
        }

        adapter.notifyDataSetChanged()
    }
}