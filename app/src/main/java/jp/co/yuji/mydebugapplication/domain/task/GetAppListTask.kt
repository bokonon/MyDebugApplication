package jp.co.yuji.mydebugapplication.domain.task

import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.provider.Settings
import jp.co.yuji.mydebugapplication.domain.model.ApplicationListDto
import jp.co.yuji.mydebugapplication.presentation.view.fragment.app.ApplicationInfoFragment
import java.util.*

/**
 * Get AppList Task.
 */
class GetAppListTask(private val packageManager: PackageManager, private val actionType: ApplicationInfoFragment.ActionType, private val listener: OnGetApplicationListListener) : AsyncTask<Void, Void, List<ApplicationListDto>>() {

    interface OnGetApplicationListListener {
        fun onGetApplicationList(appList : List<ApplicationListDto>)
    }

    override fun doInBackground(vararg params: Void?): List<ApplicationListDto> {
        return getApplicationList()
    }

    override fun onPostExecute(appList: List<ApplicationListDto>?) {
        super.onPostExecute(appList)
        if (appList != null) {
            listener.onGetApplicationList(appList)
        }
    }

    private fun getApplicationList() : List<ApplicationListDto> {
        val list = ArrayList<ApplicationListDto>()
        when (actionType) {
            ApplicationInfoFragment.ActionType.INSTALLED -> {
                addInstalledAppList(list)
            }
            ApplicationInfoFragment.ActionType.LAUNCHER -> {
                val intent = Intent(Intent.ACTION_MAIN, null)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.HOME -> {
                val intent = Intent(Intent.ACTION_MAIN, null)
                intent.addCategory(Intent.CATEGORY_HOME)
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.SETTING -> {
                val intent = Intent(Settings.ACTION_SETTINGS)
                addLauncherApp(list, intent)
            }
        }
        return list
    }

    private fun addInstalledAppList(list: ArrayList<ApplicationListDto>) {
        val applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (applicationInfo in applicationInfoList) {
            val packageName = applicationInfo.packageName
            val appName = packageManager.getApplicationLabel(applicationInfo).toString()
            val icon = packageManager.getApplicationIcon(packageName)
            list.add(ApplicationListDto(appName, packageName, icon))
        }
    }

    private fun addLauncherApp(list: ArrayList<ApplicationListDto>, intent: Intent) {
        val resolverInfoList = packageManager.queryIntentActivities(intent, 0)
        for (resolverInfo in resolverInfoList) {
            try {
                val packageName = resolverInfo.activityInfo.packageName
                val appName = packageManager.getApplicationLabel(
                        packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString()
                val icon = packageManager.getApplicationIcon(packageName)
                list.add(ApplicationListDto(appName, packageName, icon))
            } catch (e: Exception) {
                println(e.message)
            }

        }
    }
}