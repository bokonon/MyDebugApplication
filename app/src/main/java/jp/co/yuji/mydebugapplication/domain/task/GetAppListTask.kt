package jp.co.yuji.mydebugapplication.domain.task

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.*
import com.google.android.gms.actions.NoteIntents
import com.google.android.gms.actions.ReserveIntents
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
            ApplicationInfoFragment.ActionType.ALARM -> {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.TIMER -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val intent = Intent(AlarmClock.ACTION_SET_TIMER)
                    addLauncherApp(list, intent)
                }
            }
            ApplicationInfoFragment.ActionType.CALENDAR -> {
                val intent = Intent(Intent.ACTION_INSERT)
                intent.data = CalendarContract.Events.CONTENT_URI
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.CAMERA -> {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.CONTACT -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.MAIL -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.FILE -> {
                val intent = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    Intent(Intent.ACTION_GET_CONTENT)
                } else {
                    Intent(Intent.ACTION_OPEN_DOCUMENT)
                }
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/jpeg"
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.TAXI -> {
                val intent = Intent(ReserveIntents.ACTION_RESERVE_TAXI_RESERVATION)
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.MAP -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.6,-122.3"))
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.MEDIA_SEARCH -> {
                val intent = Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH)
                intent.putExtra(MediaStore.EXTRA_MEDIA_FOCUS, "vnd.android.cursor.item/*")
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.NOTE -> {
                val intent = Intent(NoteIntents.ACTION_CREATE_NOTE)
                intent.type = "*/*"
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.DIAL -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:012345678"))
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.MESSAGE -> {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("smsto:")
                addLauncherApp(list, intent)
            }
            ApplicationInfoFragment.ActionType.BROWSER -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))
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
        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.MATCH_ALL
        } else {
            PackageManager.MATCH_DEFAULT_ONLY
        }
        val resolverInfoList = packageManager.queryIntentActivities(intent, flag)
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