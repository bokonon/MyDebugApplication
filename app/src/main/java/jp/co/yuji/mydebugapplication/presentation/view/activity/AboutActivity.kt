package jp.co.yuji.mydebugapplication.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.about.AboutFragment

class AboutActivity : BaseActivity() {

    companion object {

        const val ARG_KEY = "arg_key"

        fun startActivity(activity : Activity, url: String) {
            val intent = Intent(activity, AboutActivity::class.java).apply {
                putExtra(ARG_KEY, url)
            }
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // init view
        val url = intent.getStringExtra(ARG_KEY)
        val fragment = AboutFragment.newInstance(url)
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}