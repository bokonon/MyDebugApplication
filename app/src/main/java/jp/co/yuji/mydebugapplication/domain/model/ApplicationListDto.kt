package jp.co.yuji.mydebugapplication.domain.model

import android.graphics.drawable.Drawable
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.MyFilterable

/**
 * ApplicationListDto.
 */
class ApplicationListDto(appName: String, packageName: String, icon: Drawable): MyFilterable() {
    val appName: String = appName
    val packageName: String = packageName
    val icon: Drawable = icon

    override fun getFirstFilterName(): String {
        return appName
    }
    override fun getSecondFilterName(): String {
        return packageName
    }
}