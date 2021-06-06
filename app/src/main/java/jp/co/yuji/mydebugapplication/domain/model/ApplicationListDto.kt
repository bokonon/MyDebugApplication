package jp.co.yuji.mydebugapplication.domain.model

import android.graphics.drawable.Drawable
import jp.co.yuji.mydebugapplication.presentation.view.adapter.common.MyFilterable

/**
 * ApplicationListDto.
 */
class ApplicationListDto(val appName: String, val packageName: String, val icon: Drawable): MyFilterable() {

    override fun getFirstFilterName(): String {
        return appName
    }
    override fun getSecondFilterName(): String {
        return packageName
    }
}