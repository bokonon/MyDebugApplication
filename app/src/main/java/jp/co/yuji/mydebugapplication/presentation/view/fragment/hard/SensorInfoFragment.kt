package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.adapter.SensorInfoRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common.view.*
import java.util.*

/**
 * Sensor Info Fragment.
 */
class SensorInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return SensorInfoFragment()
        }
    }

    private val listener = object: SensorInfoRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(sensor: Sensor) {
            val fragment = SensorDetailFragment.newInstance(sensor.type)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    private var sensorManager: SensorManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common, container, false)
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        view.recyclerView.layoutManager = LinearLayoutManager(activity)

        if (activity != null) {
            val adapter = SensorInfoRecyclerViewAdapter(requireActivity(), getSensorInfo())
            view.recyclerView.adapter = adapter
            adapter.setOnItemClickListener(listener)
        }

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_sensor_info
    }

    private fun getSensorInfo() : List<Sensor> {
        val list = sensorManager?.getSensorList(Sensor.TYPE_ALL)
        if (list != null) {
            return list
        }
        return ArrayList()
    }
}