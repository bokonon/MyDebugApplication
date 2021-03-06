package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
                Type.DISPLAY -> fragment = DisplayInfoFragment.newInstance()
                Type.CAMERA -> fragment = CameraInfoFragment.newInstance()
                Type.CPU -> fragment = CpuInfoFragment.newInstance()
                Type.MEMORY -> fragment = MemoryInfoFragment.newInstance()
                Type.BATTERY -> fragment = BatteryInfoFragment.newInstance()
                Type.STORAGE -> fragment = StorageInfoFragment.newInstance()
                Type.SOUND -> fragment = SoundInfoFragment.newInstance()
                Type.TELEPHONE -> fragment = TelephoneInfoFragment.newInstance()
            }
            if (fragment != null) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, fragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
            postLogEvent("hardware type: ${type?.title}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_hard_info, container, false)
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
        DISPLAY("Display", 1),
        CAMERA("Camera", 2),
        CPU("CPU", 3),
        MEMORY("Memory", 4),
        BATTERY("Battery", 5),
        STORAGE("Storage", 6),
        SOUND("Sound", 7),
        TELEPHONE("Telephone", 8);

        companion object {
            @Nullable
            fun find(position: Int): Type? {
                return Type.values().firstOrNull { it.position == position }
            }
        }
    }
}