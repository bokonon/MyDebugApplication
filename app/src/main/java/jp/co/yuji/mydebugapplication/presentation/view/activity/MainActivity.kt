package jp.co.yuji.mydebugapplication.presentation.view.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.FragmentManager
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.app.ApplicationInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.device.DeviceInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.hard.HardwareInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.other.OtherInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity.
 */
class MainActivity : BaseActivity(), AdvertisableActivity {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        when (item.itemId) {
            R.id.navigation_device_info -> {
                val fragment = DeviceInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                postLogEvent("device info")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_app_info -> {
                val fragment = ApplicationInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                postLogEvent("app info")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hard_info -> {
                val fragment = HardwareInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                postLogEvent("hard info")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_other_info -> {
                val fragment = OtherInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                postLogEvent("other info")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // init view
        val fragment = DeviceInfoFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}
