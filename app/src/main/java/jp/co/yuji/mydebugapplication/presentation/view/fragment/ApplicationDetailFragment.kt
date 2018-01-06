package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.view.adapter.ApplicationDetailRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_app_detail.view.*
import java.util.*

/**
 * Created by yuji on 2018/01/01.
 */
class ApplicationDetailFragment : BaseFragment() {

    companion object {

        val ARG_KEY = "arg_key"

        fun newInstance(packageName : String) : Fragment {
            val fragment = ApplicationDetailFragment()
            val bundle = Bundle()
            bundle.putString(ARG_KEY, packageName)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_app_detail, container, false)

        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val packageName = arguments.getString(ApplicationDetailFragment.ARG_KEY)
        val adapter = ApplicationDetailRecyclerViewAdapter(activity, getApplicationDetail(packageName))
        view.recyclerView.adapter = adapter

        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        view.recyclerView.addItemDecoration(itemDecoration)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_application_detail
    }

    private fun getApplicationDetail(packageName: String): List<CommonDto> {
        val list = ArrayList<CommonDto>()

        try {
            val applicationInfo = activity.packageManager.getApplicationInfo(packageName, 0)

            // field
            if (applicationInfo.taskAffinity != null) {
                list.add(CommonDto("taskAffinity", applicationInfo.taskAffinity))
            } else {
                list.add(CommonDto("taskAffinity", "null"))
            }
            if (applicationInfo.permission != null) {
                list.add(CommonDto("permission", applicationInfo.permission))
            } else {
                list.add(CommonDto("permission", "null"))
            }
            if (applicationInfo.processName != null) {
                list.add(CommonDto("processName", applicationInfo.processName))
            } else {
                list.add(CommonDto("processName", "null"))
            }
            if (applicationInfo.className != null) {
                list.add(CommonDto("className", applicationInfo.className))
            } else {
                list.add(CommonDto("className", "null"))
            }
            if (applicationInfo.descriptionRes > 0) {
                list.add(CommonDto("descriptionRes", activity.getString(applicationInfo.descriptionRes)))
            } else {
                list.add(CommonDto("descriptionRes", "no resource"))
            }
            list.add(CommonDto("theme", applicationInfo.theme.toString()))
            if (applicationInfo.manageSpaceActivityName != null) {
                list.add(CommonDto("manageSpaceActivityName", applicationInfo.manageSpaceActivityName))
            } else {
                list.add(CommonDto("manageSpaceActivityName", "null"))
            }
            if (applicationInfo.backupAgentName != null) {
                list.add(CommonDto("backupAgentName", applicationInfo.backupAgentName))
            } else {
                list.add(CommonDto("backupAgentName", "null"))
            }
            list.add(CommonDto("uiOptions", applicationInfo.uiOptions.toString()))
            list.add(CommonDto("flags", applicationInfo.flags.toString()))
            list.add(CommonDto("requiresSmallestWidthDp", applicationInfo.requiresSmallestWidthDp.toString()))
            list.add(CommonDto("compatibleWidthLimitDp", applicationInfo.compatibleWidthLimitDp.toString()))
            list.add(CommonDto("largestWidthLimitDp", applicationInfo.largestWidthLimitDp.toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(CommonDto("storageUuid", applicationInfo.storageUuid.toString()))
            }
            if (applicationInfo.sourceDir != null) {
                list.add(CommonDto("sourceDir", applicationInfo.sourceDir))
            } else {
                list.add(CommonDto("sourceDir", "null"))
            }
            if (applicationInfo.publicSourceDir != null) {
                list.add(CommonDto("publicSourceDir", applicationInfo.publicSourceDir))
            } else {
                list.add(CommonDto("publicSourceDir", "null"))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (applicationInfo.splitNames != null) {
                    list.add(CommonDto("splitNames", applicationInfo.splitNames.toString()))
                } else {
                    list.add(CommonDto("splitNames", "null"))
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (applicationInfo.splitSourceDirs != null) {
                    list.add(CommonDto("splitSourceDirs", applicationInfo.splitSourceDirs.toString()))
                } else {
                    list.add(CommonDto("splitSourceDirs", "null"))
                }
                if (applicationInfo.splitPublicSourceDirs != null) {
                    list.add(CommonDto("splitPublicSourceDirs", applicationInfo.splitPublicSourceDirs.toString()))
                } else {
                    list.add(CommonDto("splitPublicSourceDirs", "null"))
                }
            }
            if (applicationInfo.sharedLibraryFiles != null) {
                list.add(CommonDto("sharedLibraryFiles", applicationInfo.sharedLibraryFiles.toString()))
            } else {
                list.add(CommonDto("sharedLibraryFiles", "null"))
            }
            if (applicationInfo.dataDir != null) {
                list.add(CommonDto("dataDir", applicationInfo.dataDir))
            } else {
                list.add(CommonDto("dataDir", ""))
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (applicationInfo.deviceProtectedDataDir != null) {
                    list.add(CommonDto("deviceProtectedDataDir", applicationInfo.deviceProtectedDataDir))
                } else {
                    list.add(CommonDto("deviceProtectedDataDir", "null"))
                }
            }
            if (applicationInfo.nativeLibraryDir != null) {
                list.add(CommonDto("nativeLibraryDir", applicationInfo.nativeLibraryDir))
            } else {
                list.add(CommonDto("nativeLibraryDir", "null"))
            }
            list.add(CommonDto("uid", applicationInfo.uid.toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                list.add(CommonDto("minSdkVersion", applicationInfo.minSdkVersion.toString()))
            }
            list.add(CommonDto("targetSdkVersion", applicationInfo.targetSdkVersion.toString()))
            list.add(CommonDto("enabled", applicationInfo.enabled.toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                list.add(CommonDto("category", applicationInfo.category.toString()))
            }
        } catch (e: Exception) {
            println(e.message)
        }

        return list
    }

}