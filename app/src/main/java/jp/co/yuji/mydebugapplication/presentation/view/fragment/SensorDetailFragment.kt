package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.co.yuji.mydebugapplication.R
import kotlinx.android.synthetic.main.fragment_sensor_detail.view.*


/**
 * Created by yuji on 2018/01/04.
 */
class SensorDetailFragment : BaseFragment() {

    companion object {

        val ARG_KEY = "arg_key"

        val SENSOR_TYPE_DEFAULT = -1

        fun newInstance(type : Int) : Fragment {
            val fragment = SensorDetailFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var sensorManager: SensorManager? = null

    private var sensorType: Int = SENSOR_TYPE_DEFAULT

    private var sensorList: List<Sensor>? = null

    private var sensor: Sensor? = null

    private var accuracyChangeText: TextView? = null

    private var sensorChangeText: TextView? = null

    private var listener = object: SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            accuracyChangeText?.text = "type : " + sensor.type.toString() + "\naccuracy : " + accuracy.toString()
        }

        override fun onSensorChanged(event: SensorEvent) {
            val stringBuilder = StringBuilder()
            val values = event.values
            if (values != null && !values.isEmpty()) {
                stringBuilder.append("values.size : " + values.size.toString() + "\n")
                for (i in 0 until values.size) {
                    stringBuilder.append("values[" + i.toString() + "] : " + values[i] + "\n")
                }
            }
            sensorChangeText?.text = stringBuilder.toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_sensor_detail, container, false)
        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorType = arguments.getInt(SensorDetailFragment.ARG_KEY)
        sensorList = sensorManager?.getSensorList(sensorType)
        sensor = sensorList?.get(0)

        if (sensor != null) {
            view.sensorText?.text = getSensorBaseText(sensor!!)
        }
        accuracyChangeText = view.accuracyChangedText
        sensorChangeText = view.sensorChangeText
        return view
    }

    override fun onResume() {
        super.onResume()
        println("onResume")

        if (sensorType != SENSOR_TYPE_DEFAULT && sensor != null) {
            sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        println("onPause")

        if (sensorType != SENSOR_TYPE_DEFAULT) {
            sensorManager?.unregisterListener(listener)
        }
    }

    override fun getTitle(): Int {
        return R.string.screen_name_sensor_detail
    }

    private fun getSensorBaseText(s: Sensor): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("name : " + s.name + "\n")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            stringBuilder.append("fifoMaxEventCount : " + s.fifoMaxEventCount.toString() + "\n")
            stringBuilder.append("fifoReservedEventCount : " + s.fifoReservedEventCount.toString() + "\n")

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stringBuilder.append("highestDirectReportRateLevel : " + s.highestDirectReportRateLevel.toString() + "\n")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stringBuilder.append("id : " + s.id + "\n")
            stringBuilder.append("isAdditionalInfoSupported : " + s.isAdditionalInfoSupported.toString() + "\n")
            stringBuilder.append("isDynamicSensor : " + s.isDynamicSensor.toString() + "\n")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stringBuilder.append("isWakeUpSensor : " + s.isWakeUpSensor.toString() + "\n")
            stringBuilder.append("maxDelay : " + s.maxDelay.toString() + "\n")
        }


        stringBuilder.append("maximumRange : " + s.maximumRange.toString() + "\n")
        stringBuilder.append("power : " + s.power.toString() + "\n")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            stringBuilder.append("reportingMode : " + s.reportingMode.toString() + "\n")
        }

        stringBuilder.append("resolution : " + s.resolution.toString() + "\n")
        stringBuilder.append("minDelay : " + s.minDelay.toString() + "\n")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            stringBuilder.append("stringType : " + s.stringType + "\n")
        }
        stringBuilder.append("vendor : " + s.vendor + "\n")
        stringBuilder.append("version : " + s.version + "\n")
        return stringBuilder.toString()
    }

}