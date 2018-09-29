package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.annotation.TargetApi
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import jp.co.yuji.mydebugapplication.presentation.view.receiver.MyDeviceAdminReceiver
import kotlinx.android.synthetic.main.fragment_activity_pinning.view.*

/**
 * Pinning Activity Fragment.
 */
class PinningActivityFragment : BaseFragment() {

    companion object {

        const val ARG_KEY = "arg_key"

        fun newInstance(type : Int) : Fragment {
            val fragment = PinningActivityFragment()
            val bundle = Bundle()
            bundle.putInt(PinningActivityFragment.ARG_KEY, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    enum class PinningType(val type: Int, val strResId: Int)  {
        PINNING(1, R.string.screen_name_pinning),
        LOCK_TASK(2, R.string.screen_name_lock_task)
    }

    private var devicePolicyManager : DevicePolicyManager? = null

    private var deviceAdmin : ComponentName? = null

    private var activityManager : ActivityManager? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_activity_pinning, container, false)
        val type = arguments.getInt(PinningActivityFragment.ARG_KEY)
        val pinningType = PinningType.values().first { type == it.type }
        setTitleLazy(pinningType.strResId)

        devicePolicyManager = activity?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
        deviceAdmin = ComponentName(activity, MyDeviceAdminReceiver::class.java)

        activityManager = activity.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager

        when (pinningType) {
            PinningType.PINNING -> {
                startPinning()
                view.finishPinningActivityButton?.setText(R.string.finish_pinning_activity)
            }
            PinningType.LOCK_TASK -> {
                startLockTask()
                view.finishPinningActivityButton?.setText(R.string.finish_lock_task_activity)
            }
        }
        view.finishPinningActivityButton?.setOnClickListener { stopPinning() }

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_pinning
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startPinning() {
        executeForApiLevel21orHigher {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (activityManager != null && !activityManager!!.isInLockTaskMode) {
                    activity?.startLockTask()
                }
            } else {
                println(activityManager?.lockTaskModeState)
                if (activityManager?.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_NONE) {
                    activity?.startLockTask()
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startLockTask() {
        if (devicePolicyManager != null && devicePolicyManager!!.isDeviceOwnerApp(activity.packageName)) {
            devicePolicyManager?.setLockTaskPackages(deviceAdmin, arrayOf(activity.packageName))
            startPinning()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun stopPinning() {
        executeForApiLevel21orHigher {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                if (activityManager != null && activityManager!!.isInLockTaskMode) {
                    activity?.stopLockTask()
                }
            } else {
                if (activityManager?.lockTaskModeState != ActivityManager.LOCK_TASK_MODE_NONE) {
                    activity?.stopLockTask()
                }
            }
        }
        activity?.finish()
    }

    private fun executeForApiLevel21orHigher(execute: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(activity, R.string.error_support_api_level_21, Toast.LENGTH_LONG).show()
            return
        }
        execute()
    }
}