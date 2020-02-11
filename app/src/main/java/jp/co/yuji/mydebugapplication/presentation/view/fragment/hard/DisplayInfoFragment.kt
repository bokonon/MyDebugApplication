package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.DisplayMetrics
import android.view.*
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonDetailRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common.view.*
import java.util.*


class DisplayInfoFragment  : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return DisplayInfoFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_common, container, false)


        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        if (activity != null) {
            val adapter = CommonDetailRecyclerViewAdapter(activity!!, getDisplayInfo())
            view.recyclerView.adapter = adapter
        }

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_display_info
    }

    private fun getDisplayInfo() : ArrayList<CommonDto> {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display: Display = windowManager.defaultDisplay
        val list = ArrayList<CommonDto>()
        list.add(CommonDto("DisplayId", display.displayId.toString()))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            list.add(CommonDto("isValid", display.isValid.toString()))
            list.add(CommonDto("Flags", display.flags.toString()))
            list.add(CommonDto("Name", display.name))

            val realSize = Point()
            display.getRealSize(realSize)
            list.add(CommonDto("RealSize x", realSize.x.toString()))
            list.add(CommonDto("RealSize y", realSize.y.toString()))
        }
        val size = Point()
        display.getSize(size)
        list.add(CommonDto("Size x", size.x.toString()))
        list.add(CommonDto("Size y", size.y.toString()))

        val smallestSize = Point()
        val largestSize = Point()
        display.getCurrentSizeRange(smallestSize, largestSize)
        list.add(CommonDto("SmallestSize x", smallestSize.x.toString()))
        list.add(CommonDto("SmallestSize y", smallestSize.y.toString()))
        list.add(CommonDto("LargestSize x", largestSize.x.toString()))
        list.add(CommonDto("LargestSize y", largestSize.y.toString()))

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        list.add(CommonDto("DisplayMetrics.widthPixels", displayMetrics.widthPixels.toString()))
        list.add(CommonDto("DisplayMetrics.heightPixels", displayMetrics.heightPixels.toString()))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val displayRealMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getRealMetrics(displayRealMetrics)
            list.add(CommonDto("DisplayRealMetrics.widthPixels", displayRealMetrics.widthPixels.toString()))
            list.add(CommonDto("DisplayRealMetrics.heightPixels", displayRealMetrics.heightPixels.toString()))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val mode = display.mode
            list.add(CommonDto("Mode id", mode.modeId.toString()))
            list.add(CommonDto("Mode physicalWidth", mode.physicalWidth.toString()))
            list.add(CommonDto("Mode physicalHeight", mode.physicalHeight.toString()))
            list.add(CommonDto("Mode refreshRate", mode.refreshRate.toString()))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val hdrCapabilities = display.hdrCapabilities
            list.add(CommonDto("HdrCapabilities desiredMaxLuminance",
                    hdrCapabilities.desiredMaxLuminance.toString()))
            list.add(CommonDto("HdrCapabilities desiredMaxAverageLuminance",
                    hdrCapabilities.desiredMaxAverageLuminance.toString()))
            list.add(CommonDto("HdrCapabilities desiredMinLuminance",
                    hdrCapabilities.desiredMinLuminance.toString()))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            list.add(CommonDto("isHdr", display.isHdr.toString()))
            list.add(CommonDto("isWideColorGamut", display.isWideColorGamut.toString()))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            list.add(CommonDto("appVsyncOffsetNanos", display.appVsyncOffsetNanos.toString()))
            list.add(CommonDto("presentationDeadlineNanos", display.presentationDeadlineNanos.toString()))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            list.add(CommonDto("state", display.state.toString()))
        }




        return list
    }
}