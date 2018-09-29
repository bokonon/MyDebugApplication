package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonInfoRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_hard_info.view.*
import java.util.*

/**
 * Hardware Info Fragment.
 */
class HardwareInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return HardwareInfoFragment()
        }
    }

    private val listener = object: CommonInfoRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            var fragment: Fragment? = null
            val type = Type.find(position)
            when (type) {
                Type.SENSOR -> fragment = SensorInfoFragment.newInstance()
                Type.CAMERA -> fragment = CameraInfoFragment.newInstance()
                Type.CPU -> fragment = CpuInfoFragment.newInstance()
                Type.MEMORY -> fragment = MemoryInfoFragment.newInstance()
                Type.BATTERY -> fragment = BatteryInfoFragment.newInstance()
            }
            if (fragment != null) {
                activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_hard_info, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = CommonInfoRecyclerViewAdapter(getHardwareInfo())
        view.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        // ad
        val adRequest = AdRequest.Builder().build()
        view.adView.loadAd(adRequest)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_hardware_info
    }

    private fun getHardwareInfo() : List<String> {
        val list = ArrayList<String>()

        for (type in Type.values()) {
            list.add(type.position, type.title)
        }

        return list
    }

    enum class Type(val title: String, val position: Int)  {
        SENSOR("Sensor", 0),
        CAMERA("Camera", 1),
        CPU("CPU", 2),
        MEMORY("Memory", 3),
        BATTERY("Battery", 4);

        companion object {
            @Nullable
            fun find(position: Int): Type? {
                return Type.values().firstOrNull { it.position == position }
            }
        }
    }
}