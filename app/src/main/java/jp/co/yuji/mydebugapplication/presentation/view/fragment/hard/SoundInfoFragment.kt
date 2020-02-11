package jp.co.yuji.mydebugapplication.presentation.view.fragment.hard

import android.annotation.TargetApi
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_common.view.*
import java.util.ArrayList
import android.media.AudioManager
import android.content.Context
import android.os.Build
import jp.co.yuji.mydebugapplication.presentation.view.adapter.SoundInfoRecyclerViewAdapter


/**
 * Sound Info Fragment.
 */
class SoundInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return SoundInfoFragment()
        }
    }

    private lateinit var adapter: SoundInfoRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(jp.co.yuji.mydebugapplication.R.layout.fragment_common, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)

        if (activity != null) {
            adapter = SoundInfoRecyclerViewAdapter(activity!!, getSoundInfo())
            view.recyclerView.adapter = adapter
        }

        return view
    }

    override fun getTitle(): Int {
        return jp.co.yuji.mydebugapplication.R.string.screen_name_sound_info
    }

    private fun getSoundInfo(): ArrayList<CommonDto> {
        val list = ArrayList<CommonDto>()
        val audioManager = activity?.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val common = CommonDto("=== common ===", "")
        list.add(common)

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            val isVolumeFixedDto = CommonDto("isVolumeFixed", audioManager.isVolumeFixed.toString())
            list.add(isVolumeFixedDto)
        }

        val ringerMode = CommonDto("ringerMode", audioManager.ringerMode.toString())
        list.add(ringerMode)

        val isSpeakerphoneOn = CommonDto("isSpeakerphoneOn", audioManager.isSpeakerphoneOn.toString())
        list.add(isSpeakerphoneOn)

        val isBluetoothScoAvailableOffCall = CommonDto("isBluetoothScoAvailableOffCall", audioManager.isBluetoothScoAvailableOffCall.toString())
        list.add(isBluetoothScoAvailableOffCall)

        val isBluetoothScoOn = CommonDto("isBluetoothScoOn", audioManager.isBluetoothScoOn.toString())
        list.add(isBluetoothScoOn)

        val isBluetoothA2dpOn = CommonDto("isBluetoothA2dpOn", audioManager.isBluetoothA2dpOn.toString())
        list.add(isBluetoothA2dpOn)

        val isWiredHeadsetOn = CommonDto("isWiredHeadsetOn", audioManager.isWiredHeadsetOn.toString())
        list.add(isWiredHeadsetOn)

        val isMicrophoneMute = CommonDto("isMicrophoneMute", audioManager.isMicrophoneMute.toString())
        list.add(isMicrophoneMute)

        val mode = CommonDto("mode", audioManager.mode.toString())
        list.add(mode)

        val isMusicActive = CommonDto("isMusicActive", audioManager.isMusicActive.toString())
        list.add(isMusicActive)

        for (value in StreamType.values()) {
            val titleDto = CommonDto("=== " + value.title + " ===", "")
            list.add(titleDto)

            val streamVolume = audioManager.getStreamVolume(value.type)
            val streamVolumeDto = CommonDto("streamVolume", streamVolume.toString())
            list.add(streamVolumeDto)

            val streamMaxVolume = audioManager.getStreamMaxVolume(value.type)
            val streamMaxVolumeDto = CommonDto("streamMaxVolume", streamMaxVolume.toString())
            list.add(streamMaxVolumeDto)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val isStreamMute = audioManager.isStreamMute(value.type)
                val isStreamMuteDto = CommonDto("isStreamMute", isStreamMute.toString())
                list.add(isStreamMuteDto)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (value in StreamTypeFromOreo.values()) {
                val titleDto = CommonDto("=== " + value.title + " ===", "")
                list.add(titleDto)

                val streamVolume = audioManager.getStreamVolume(value.type)
                val streamVolumeDto = CommonDto("streamVolume", streamVolume.toString())
                list.add(streamVolumeDto)

                val streamMaxVolume = audioManager.getStreamMaxVolume(value.type)
                val streamMaxVolumeDto = CommonDto("streamMaxVolume", streamMaxVolume.toString())
                list.add(streamMaxVolumeDto)

                val isStreamMute = audioManager.isStreamMute(value.type)
                val isStreamMuteDto = CommonDto("isStreamMute", isStreamMute.toString())
                list.add(isStreamMuteDto)
            }
        }

        return list
    }

    enum class StreamType(val title: String, val type: Int) {
        STREAM_VOICE_CALL("STREAM_VOICE_CALL", AudioManager.STREAM_VOICE_CALL),
        STREAM_SYSTEM("STREAM_SYSTEM", AudioManager.STREAM_SYSTEM),
        STREAM_RING("STREAM_RING", AudioManager.STREAM_RING),
        STREAM_MUSIC("STREAM_MUSIC", AudioManager.STREAM_MUSIC),
        STREAM_ALARM("STREAM_ALARM", AudioManager.STREAM_ALARM),
        STREAM_NOTIFICATION("STREAM_NOTIFICATION", AudioManager.STREAM_NOTIFICATION),
        STREAM_DTMF("STREAM_DTMF", AudioManager.STREAM_DTMF);
    }

    @TargetApi(Build.VERSION_CODES.O)
    enum class StreamTypeFromOreo(val title: String, val type: Int) {
        STREAM_ACCESSIBILITY("STREAM_ACCESSIBILITY", AudioManager.STREAM_ACCESSIBILITY);
    }

}