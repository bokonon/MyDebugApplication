package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonInfoRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common_progress.view.*
import java.util.*

class PortListFragment: BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return PortListFragment()
        }
    }

    private val listener = object:CommonInfoRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            val command = ActionType.find(position)?.command
            if (command == null) {
                postLogEvent("command is null")
                return
            }
            val fragment = PortDetailFragment.newInstance(command)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = CommonInfoRecyclerViewAdapter(getPortList())
        view.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_port_list
    }

    private fun getPortList() : List<String> {
        val list = ArrayList<String>()

        for (type in ActionType.values()) {
            list.add(type.position, type.title)
        }

        return list
    }

    enum class ActionType(val title: String, val position: Int, val command: String)  {
        ALL("Listen Port", 0, "netstat -lp"),
        TCP("TCP", 1, "netstat -tp"),
        UDP("UDP", 2, "netstat -up");

        companion object {
            @Nullable
            fun find(position: Int): ActionType? {
                return values().firstOrNull { it.position == position }
            }
        }
    }

}