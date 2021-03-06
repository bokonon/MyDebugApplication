package jp.co.yuji.mydebugapplication.presentation.view.fragment.app

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
import kotlinx.android.synthetic.main.fragment_app_info.view.*
import java.util.*

/**
 * Application Info Fragment.
 */
class ApplicationInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return ApplicationInfoFragment()
        }
    }

    private val listener = object:CommonInfoRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            val fragment = ApplicationListFragment.newInstance(position)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, fragment)
                    ?.addToBackStack(null)
                    ?.commit()
            postLogEvent("action type: $position")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_app_info, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = CommonInfoRecyclerViewAdapter(getApplicationInfo())
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
        return R.string.screen_name_application_info
    }

    private fun getApplicationInfo() : List<String> {
        val list = ArrayList<String>()

        for (type in ActionType.values()) {
            list.add(type.position, type.title)
        }

        return list
    }

    enum class ActionType(val title: String, val position: Int)  {
        INSTALLED("Installed App", 0),
        LAUNCHER("Launcher App", 1),
        HOME("Home App", 2),
        SETTING("Setting App", 3),
        ALARM("Alarm App", 4),
        TIMER("Timer App", 5),
        CALENDAR("Calendar App", 6),
        CAMERA("Camera App", 7),
        CONTACT("Contact App", 8),
        MAIL("Mail App", 9),
        FILE("File App", 10),
        TAXI("Taxi app", 11),
        MAP("Map App", 12),
        MEDIA_SEARCH("Media Search App", 13),
        NOTE("Note App", 14),
        DIAL("Dial App", 15),
        MESSAGE("Message App", 16),
        BROWSER("Browser App", 17);

        companion object {
            @Nullable
            fun find(position: Int): ActionType? {
                return values().firstOrNull { it.position == position }
            }
        }
    }

}