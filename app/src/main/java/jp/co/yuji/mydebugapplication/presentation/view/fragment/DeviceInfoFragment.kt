package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.DeviceInfoRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_device_info.view.*
import java.util.*

/**
 * Created by yuji on 2017/12/29.
 */
class DeviceInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return DeviceInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_device_info, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = DeviceInfoRecyclerViewAdapter(activity, getDeviceInfo())
        view.recyclerView.adapter = adapter

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_device_info
    }

    private fun getDeviceInfo() : ArrayList<CommonDto> {
        val list = ArrayList<CommonDto>()
        addBuildInfo(list)
        addScreenInfo(list)
        addMemoryInfo(list)
        addTimeInfo(list)
        return list
    }

    private fun addBuildInfo(list : ArrayList<CommonDto>) {
        list.add(CommonDto("Android", android.os.Build.VERSION.RELEASE))
        list.add(CommonDto("API Level", (android.os.Build.VERSION.SDK_INT).toString()))
        list.add(CommonDto("Device", android.os.Build.DEVICE))
        list.add(CommonDto("Model", android.os.Build.MODEL))
        list.add(CommonDto("Product", android.os.Build.PRODUCT))
    }

    private fun addScreenInfo(list : ArrayList<CommonDto>) {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        list.add(CommonDto("Resolution", screenWidth.toString() + " X " + screenHeight.toString()))

        val metrics = resources.displayMetrics
        list.add(CommonDto("Xdpi", metrics.xdpi.toString() + " dpi"))
        list.add(CommonDto("Ydpi", metrics.ydpi.toString() + " dpi"))
        list.add(CommonDto("Density", metrics.density.toString()))
        list.add(CommonDto("DensityDpi", metrics.densityDpi.toString() + " dpi"))
        list.add(CommonDto("ScaledDensity", metrics.scaledDensity.toString()))
    }

    private fun addMemoryInfo(list : ArrayList<CommonDto>) {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        list.add(CommonDto("Total Memory", (memoryInfo.totalMem/1024/1024).toString() + " MB"))
        list.add(CommonDto("Available Memory", (memoryInfo.availMem/1024/1024).toString() + " MB"))
        list.add(CommonDto("LowMemory Threshold", (memoryInfo.threshold/1024/1024).toString() + " MB"))
        list.add(CommonDto("LowMemory?", memoryInfo.lowMemory.toString()))
    }

    private fun addTimeInfo(list : ArrayList<CommonDto>) {
        val timeZone = TimeZone.getDefault ()
        list.add(CommonDto("TimeZone", timeZone.displayName))
        val uptime = SystemClock.uptimeMillis()
        val day = (uptime/1000/60/60/24).toInt()
        val remainedDay = uptime%(1000*60*60*24)
        val hour = (remainedDay/1000/60/60).toInt()
        val remainedHour = remainedDay%(1000*60*60)
        val minutes = (remainedHour/1000/60).toInt()
        list.add(CommonDto("Uptime", day.toString() + " days "
                + hour.toString() + " h "
                + minutes.toString() + " m"))
    }

}