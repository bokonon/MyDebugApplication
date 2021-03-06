package jp.co.yuji.mydebugapplication.presentation.view.fragment.other

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.activity.AboutActivity
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.CommonInfoRecyclerViewAdapter
import jp.co.yuji.mydebugapplication.presentation.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_other_info.view.*
import java.util.*



/**
 * Other Info Fragment.
 */
class OtherInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return OtherInfoFragment()
        }
    }

    private val listener = object: CommonInfoRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            var fragment: Fragment? = null
            val type = Type.find(position)
            when (type) {
                Type.SYSTEM_PROPERTIES -> fragment = SystemPropertiesFragment.newInstance()
                Type.LOG -> fragment = LogDetailFragment.newInstance()
                Type.WI_FI -> fragment = WiFiInfoListFragment.newInstance()
                Type.EXEC_SHELL -> fragment = ExecShellFragment.newInstance()
                Type.PINNING -> fragment = PinningFragment.newInstance()
                Type.NETWORK_INFO -> fragment = NetworkInfoFragment.newInstance()
                Type.ACTIVITY_MANAGER -> fragment = ActivityManagerFragment.newInstance()
                Type.ALARM_MANAGER -> fragment = AlarmManagerFragment.newInstance()
                Type.PORT -> fragment = PortListFragment.newInstance()
            }
            if (fragment != null) {
                activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.container, fragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
            postLogEvent("other type: ${type?.title}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_other_info, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = CommonInfoRecyclerViewAdapter(getOtherInfo())
        view.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(listener)

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        // ad
        val adRequest = AdRequest.Builder().build()
        view.adView.loadAd(adRequest)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu to use in the action bar
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.menu_privacy_policy -> {
                startActivity("https://bokonon.github.io/privacy/", "privacy policy")
                return true
            }
            R.id.menu_license -> {
                startActivity("file:///android_asset/license.html", "license")
                return true
            }
            R.id.menu_version -> {
                startActivity("file:///android_asset/version.html", "version")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getTitle(): Int {
        return R.string.screen_name_other_info
    }

    private fun getOtherInfo() : List<String> {
        val list = ArrayList<String>()

        for (type in Type.values()) {
            list.add(type.position, type.title)
        }

        return list
    }

    private fun startActivity(url: String, contentType: String) {
        postLogEvent(contentType)
        if (activity != null) {
            AboutActivity.startActivity(requireActivity(), url)
        }
    }

    enum class Type(val title: String, val position: Int)  {
        SYSTEM_PROPERTIES("System Properties", 0),
        LOG("Log", 1),
        WI_FI("Wi-Fi", 2),
        EXEC_SHELL("Exec Shell", 3),
        PINNING("Pinning", 4),
        NETWORK_INFO("Network Info", 5),
        ACTIVITY_MANAGER("Activity Manager", 6),
        ALARM_MANAGER("Alarm Manager", 7),
        PORT("Port", 8);

        companion object {
            @Nullable
            fun find(position: Int): Type? {
                return Type.values().firstOrNull { it.position == position }
            }
        }
    }
}