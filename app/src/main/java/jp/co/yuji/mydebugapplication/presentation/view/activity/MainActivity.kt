package jp.co.yuji.mydebugapplication.presentation.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.ApplicationInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.DeviceInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.HardwareInfoFragment
import jp.co.yuji.mydebugapplication.presentation.view.fragment.OtherInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        when (item.itemId) {
            R.id.navigation_device_info -> {
                val fragment = DeviceInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_app_info -> {
                val fragment = ApplicationInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_hard_info -> {
                val fragment = HardwareInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_other_info -> {
                val fragment = OtherInfoFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
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
