package jp.co.yuji.mydebugapplication.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import jp.co.yuji.mydebugapplication.R

/**
 * Marker Interface.
 */
interface AdvertisableActivity

/**
 * Activity Base Class.
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        if (this is AdvertisableActivity) {
            println("this is AdvertisableActivity")
            MobileAds.initialize(this)
        }
    }

    fun postLogEvent(contentType: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }
}