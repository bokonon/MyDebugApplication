package jp.co.yuji.mydebugapplication.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.presentation.view.fragment.other.PinningActivityFragment

/**
 * Pinning Activity.
 */
class PinningActivity : AppCompatActivity() {

    companion object {

        const val ARG_KEY = "arg_key"

        fun startActivity(activity : Activity, pinningType : PinningActivityFragment.PinningType) {
            val intent = Intent(activity, PinningActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra(ARG_KEY, pinningType.type)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pinning)

        // init view
        val fragment = PinningActivityFragment.newInstance(intent.getIntExtra(ARG_KEY,
                PinningActivityFragment.PinningType.PINNING.type))
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

}