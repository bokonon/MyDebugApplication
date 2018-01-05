package jp.co.yuji.mydebugapplication.domain.model

import android.graphics.drawable.Drawable

/**
 * Created by yuji on 2017/12/31.
 */
class ApplicationListDto(appName: String, packageName: String, icon: Drawable) {
    var appName: String = appName
    var packageName: String = packageName
    var icon: Drawable = icon
}