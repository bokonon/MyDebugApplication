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
import android.widget.Button
import android.widget.Toast
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.activity.PinningActivity
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import jp.co.yuji.mydebugapplication.presentation.view.receiver.MyDeviceAdminReceiver
import kotlinx.android.synthetic.main.fragment_pinning.view.*

/**
 * Pinning Fragment.
 */
class PinningFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return PinningFragment()
        }
    }

    private var devicePolicyManager : DevicePolicyManager? = null

    private var deviceAdmin : ComponentName? = null

    private var activityManager : ActivityManager? = null

    private var clearDeviceOwnerButton : Button? = null
    private var startLockTaskButton : Button? = null
    private var stopLockTaskButton : Button? = null
    private var startLockTaskActivityButton : Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_pinning, container, false)

        devicePolicyManager = activity?.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager?
        deviceAdmin = ComponentName(activity, MyDeviceAdminReceiver::class.java)

        activityManager = activity.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager

        view.startPinningButton?.setOnClickListener { startPinning() }
        view.stopPinningButton?.setOnClickListener { stopPinning() }
        view.startPinningActivityButton?.setOnClickListener { startPinningActivity() }

//        setDeviceOwnerView(view)
//        setDeviceOwnerStatus(view.isDeviceOwnerTextView)

//        if (isDeviceOwner()) {
//            view.clearDeviceOwnerButton?.setOnClickListener { clearDeviceOwner() }
//            view.startLockTaskButton?.setOnClickListener { startLockTask() }
//            view.stopLockTaskButton?.setOnClickListener { stopLockTask() }
//            view.startLockTaskActivityButton?.setOnClickListener { startLockTaskActivity() }
//        } else {
//            disableButton()
//        }

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
    }

    private fun startPinningActivity() {
        PinningActivity.startActivity(activity, PinningActivityFragment.PinningType.PINNING)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun clearDeviceOwner() {
        executeForApiLevel21orHigher {
            if (isDeviceOwner()) {
                devicePolicyManager?.clearDeviceOwnerApp(activity.packageName)
                disableButton()
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
    private fun stopLockTask() {
        stopPinning()
    }

    private fun startLockTaskActivity() {
        PinningActivity.startActivity(activity, PinningActivityFragment.PinningType.LOCK_TASK)
    }

    private fun executeForApiLevel21orHigher(execute: () -> Unit) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(activity, R.string.error_support_api_level_21, Toast.LENGTH_LONG).show()
            return
        }
        execute()
    }

//    private fun setDeviceOwnerStatus(@NonNull view: TextView) {
//        val stringResId = if (isDeviceOwner()) R.string.is_device_owner_status_true else R.string.is_device_owner_status_false
//        view.setText(stringResId)
//    }

    private fun isDeviceOwner() : Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && devicePolicyManager != null
                && devicePolicyManager!!.isDeviceOwnerApp(activity.packageName)
    }

//    private fun setDeviceOwnerView(@NonNull view: View) {
//        clearDeviceOwnerButton = view.clearDeviceOwnerButton
//        startLockTaskButton = view.startLockTaskButton
//        stopLockTaskButton = view.stopLockTaskButton
//        startLockTaskActivityButton = view.startLockTaskActivityButton
//    }

    private fun disableButton() {
        clearDeviceOwnerButton?.isEnabled = false
        startLockTaskButton?.isEnabled = false
        stopLockTaskButton?.isEnabled = false
        startLockTaskActivityButton?.isEnabled = false
    }

}