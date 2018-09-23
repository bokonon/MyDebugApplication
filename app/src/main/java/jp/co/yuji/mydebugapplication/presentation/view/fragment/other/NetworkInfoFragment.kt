package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.NetworkInfoRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import jp.co.yuji.mydebugapplication.presentation.view.receiver.ConnectivityReceiver
import kotlinx.android.synthetic.main.fragment_device_info.view.*

class NetworkInfoFragment : BaseFragment() {

    companion object {
        fun newInstance(): Fragment {
            return NetworkInfoFragment()
        }
    }

    private lateinit var adapter : NetworkInfoRecyclerViewAdapter

    private val receiver = ConnectivityReceiver(object : ConnectivityReceiver.OnConnectivityListener {
        override fun onConnectivityReceive(networkInfo: NetworkInfo?) {
            updateView(networkInfo)
        }
    })

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_network_info, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = NetworkInfoRecyclerViewAdapter(activity, ArrayList())
        view.recyclerView.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()
        activity.registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        activity.unregisterReceiver(receiver)
    }

    override fun getTitle(): Int {
        return R.string.screen_name_network_info
    }

    private fun updateView(networkInfo: NetworkInfo?) {
        val list = adapter.getList()
        list.clear()

        if (networkInfo != null) {
            list.add(CommonDto("state", networkInfo.state.toString()))
            list.add(CommonDto("detailedState", networkInfo.detailedState.toString()))
            if (networkInfo.reason != null) {
                list.add(CommonDto("reason", networkInfo.reason))
            }
            list.add(CommonDto("extraInfo", networkInfo.extraInfo))
            list.add(CommonDto("type", networkInfo.type.toString()))
            list.add(CommonDto("typeName", networkInfo.typeName))
            list.add(CommonDto("subtype", networkInfo.subtype.toString()))
            list.add(CommonDto("subtypeName", networkInfo.subtypeName))
        } else {
            list.add(CommonDto("networkInfo", "none"))
        }


        adapter.notifyDataSetChanged()
    }
}