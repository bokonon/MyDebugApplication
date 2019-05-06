package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonSelectableRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common.view.*
import java.util.ArrayList
import android.content.Context.TELEPHONY_SERVICE
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager


class TelephoneInfoFragment : BaseFragment() {

    companion object {
        const val PERMISSIONS_REQUEST_CODE_READ_PHONE_STATE = 0
        fun newInstance() : Fragment {
            return TelephoneInfoFragment()
        }
    }

    private lateinit var adapter: CommonSelectableRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_common, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<CommonDto>()
        adapter = CommonSelectableRecyclerViewAdapter(activity, list)
        view.recyclerView.adapter = adapter

        addTelephoneInfo(list)
        addRequiredPermissionTelephoneInfo(list)
        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_telephone_info
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            addRequiredPermissionTelephoneInfo(adapter.getItems())
        }

    }

    private fun addTelephoneInfo(list: ArrayList<CommonDto>) {

        val telephonyManager = activity.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val phoneCount = telephonyManager.phoneCount
            list.add(CommonDto("phoneCount", Integer.toString(phoneCount)))
        }

        val phoneType = telephonyManager.phoneType
        list.add(CommonDto("phoneType", Integer.toString(phoneType)))

        val networkOperatorName = telephonyManager.networkOperatorName
        if (networkOperatorName != null) {
            list.add(CommonDto("networkOperatorName", networkOperatorName))
        }

        val networkOperator = telephonyManager.networkOperator
        if (networkOperator != null) {
            list.add(CommonDto("networkOperator", networkOperator))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val networkSpecifier = telephonyManager.networkSpecifier
            if (networkSpecifier != null) {
                list.add(CommonDto("networkSpecifier", networkSpecifier))
            }
        }

        val isNetworkRoaming = telephonyManager.isNetworkRoaming
        val isNetworkRoamingString = if (isNetworkRoaming) "true" else "false"
        list.add(CommonDto("isNetworkRoaming", isNetworkRoamingString))

        val networkCountryIso = telephonyManager.networkCountryIso
        if (networkCountryIso != null) {
            list.add(CommonDto("networkCountryIso", networkCountryIso))
        }

        val networkType = telephonyManager.networkType
        list.add(CommonDto("networkType", Integer.toString(networkType)))

        val hasIccCard = telephonyManager.hasIccCard()
        val hasIccCardString = if (hasIccCard) "true" else "false"
        list.add(CommonDto("hasIccCard", hasIccCardString))

        val simState = telephonyManager.simState
        list.add(CommonDto("simState", Integer.toString(simState)))

        val simOperator = telephonyManager.simOperator
        if (simOperator != null) {
            list.add(CommonDto("simOperator", simOperator))
        }

        val simOperatorName = telephonyManager.simOperatorName
        if (simOperatorName != null) {
            list.add(CommonDto("simOperatorName", simOperatorName))
        }

        val simCountryIso = telephonyManager.simCountryIso
        if (simCountryIso != null) {
            list.add(CommonDto("simCountryIso", simCountryIso))
        }

        val callState = telephonyManager.callState
        list.add(CommonDto("callState", Integer.toString(callState)))

        val dataActivity = telephonyManager.dataActivity
        list.add(CommonDto("dataActivity", Integer.toString(dataActivity)))

        val dataState = telephonyManager.dataState
        list.add(CommonDto("dataState", Integer.toString(dataState)))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val isVoiceCapable = telephonyManager.isVoiceCapable
            val isVoiceCapableString = if (isVoiceCapable) "true" else "false"
            list.add(CommonDto("isVoiceCapable", isVoiceCapableString))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val isSmsCapable = telephonyManager.isSmsCapable
            val isSmsCapableString = if (isSmsCapable) "true" else "false"
            list.add(CommonDto("isSmsCapable", isSmsCapableString))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val mmsUserAgent = telephonyManager.mmsUserAgent
            if (mmsUserAgent != null) {
                list.add(CommonDto("mmsUserAgent", mmsUserAgent))
            }

            val mmsUAProfUrl = telephonyManager.mmsUAProfUrl
            if (mmsUAProfUrl != null) {
                list.add(CommonDto("mmsUAProfUrl", mmsUAProfUrl))
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val hasCarrierPrivileges = telephonyManager.hasCarrierPrivileges()
            val hasCarrierPrivilegesString = if (hasCarrierPrivileges) "true" else "false"
            list.add(CommonDto("hasCarrierPrivileges", hasCarrierPrivilegesString))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val isConcurrentVoiceAndDataSupported = telephonyManager.isConcurrentVoiceAndDataSupported
            val isConcurrentVoiceAndDataSupportedString = if (isConcurrentVoiceAndDataSupported) "true" else "false"
            list.add(CommonDto("isConcurrentVoiceAndDataSupported", isConcurrentVoiceAndDataSupportedString))

            val isDataEnabled = telephonyManager.isDataEnabled
            val isDataEnabledString = if (isDataEnabled) "true" else "false"
            list.add(CommonDto("isDataEnabled", isDataEnabledString))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val canChangeDtmfToneLength = telephonyManager.canChangeDtmfToneLength()
            val canChangeDtmfToneLengthString = if (canChangeDtmfToneLength) "true" else "false"
            list.add(CommonDto("canChangeDtmfToneLength", canChangeDtmfToneLengthString))

            val isWorldPhone = telephonyManager.isWorldPhone
            val isWorldPhoneString = if (isWorldPhone) "true" else "false"
            list.add(CommonDto("isWorldPhone", isWorldPhoneString))

            val isTtyModeSupported = telephonyManager.isTtyModeSupported
            val isTtyModeSupportedString = if (isTtyModeSupported) "true" else "false"
            list.add(CommonDto("isTtyModeSupported", isTtyModeSupportedString))

            val isHearingAidCompatibilitySupported = telephonyManager.isHearingAidCompatibilitySupported
            val isHearingAidCompatibilitySupportedString = if (isHearingAidCompatibilitySupported) "true" else "false"
            list.add(CommonDto("isHearingAidCompatibilitySupported", isHearingAidCompatibilitySupportedString))
        }

    }

    private fun addRequiredPermissionTelephoneInfo(list: ArrayList<CommonDto>) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            val permissions: Array<String> = arrayOf(Manifest.permission.READ_PHONE_STATE)
            requestPermissions(
                    permissions,
                    PERMISSIONS_REQUEST_CODE_READ_PHONE_STATE)
            return
        }
        val telephonyManager = activity.getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        val deviceSoftwareVersion = telephonyManager.deviceSoftwareVersion
        if (deviceSoftwareVersion != null) {
            list.add(CommonDto("deviceSoftwareVersion", deviceSoftwareVersion))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val imei = telephonyManager.imei
            if (imei != null) {
                list.add(CommonDto("imei", imei))
            }

            val meid = telephonyManager.meid
            if (meid != null) {
                list.add(CommonDto("meid", meid))
            }

            val carrierConfig = telephonyManager.carrierConfig
            if (carrierConfig != null) {
                list.add(CommonDto("carrierConfig", carrierConfig.toString()))
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val dataNetworkType = telephonyManager.dataNetworkType
            list.add(CommonDto("dataNetworkType", Integer.toString(dataNetworkType)))

            val voiceNetworkType = telephonyManager.voiceNetworkType
            list.add(CommonDto("voiceNetworkType", Integer.toString(voiceNetworkType)))
        }

        val simSerialNumber = telephonyManager.simSerialNumber
        if (simSerialNumber != null) {
            list.add(CommonDto("simSerialNumber", simSerialNumber))
        }

        val subscriberId = telephonyManager.subscriberId
        if (subscriberId != null) {
            list.add(CommonDto("subscriberId", subscriberId))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val groupIdLevel1 = telephonyManager.groupIdLevel1
            if (groupIdLevel1 != null) {
                list.add(CommonDto("groupIdLevel1", groupIdLevel1))
            }
        }

        val line1Number = telephonyManager.line1Number
        if (line1Number != null) {
            list.add(CommonDto("line1Number", line1Number))
        }

        val voiceMailNumber = telephonyManager.voiceMailNumber
        if (voiceMailNumber != null) {
            list.add(CommonDto("voiceMailNumber", voiceMailNumber))
        }

        val voiceMailAlphaTag = telephonyManager.voiceMailAlphaTag
        if (voiceMailAlphaTag != null) {
            list.add(CommonDto("voiceMailAlphaTag", voiceMailAlphaTag))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val allCellInfo = telephonyManager.allCellInfo
            if (allCellInfo != null && allCellInfo.size > 0) {
                list.add(CommonDto("=== CellInfo ===", ""))
                for ((num, cellInfo) in allCellInfo.withIndex()) {
                    list.add(CommonDto(Integer.toString(num), ""))
                    val isRegistered = cellInfo.isRegistered
                    val isRegisteredString = if (isRegistered) "true" else "false"
                    list.add(CommonDto("isRegistered", isRegisteredString))

                    val timeStamp = cellInfo.timeStamp
                    list.add(CommonDto("timeStamp", timeStamp.toString()))
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val forbiddenPlmns = telephonyManager.forbiddenPlmns
            if (forbiddenPlmns != null && forbiddenPlmns.isNotEmpty()) {
                list.add(CommonDto("=== forbiddenPlmns ===", ""))
                for (forbiddenPlmn in forbiddenPlmns) {
                    list.add(CommonDto("", forbiddenPlmn))
                }
            }

            val serviceState = telephonyManager.serviceState
            if (serviceState != null) {
                list.add(CommonDto("serviceState", serviceState.toString()))
            }
        }

    }
}