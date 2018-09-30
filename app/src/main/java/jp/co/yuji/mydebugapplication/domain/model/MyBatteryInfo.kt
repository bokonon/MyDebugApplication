package jp.co.yuji.mydebugapplication.domain.model

/**
 * MyBatteryInfo.
 */
class MyBatteryInfo(val health: Int,
                    val icon_small: Int,
                    val level: Int,
                    val plugged: Int,
                    val present: Boolean,
                    val scale: Int,
                    val status: Int,
                    val technology: String,
                    val temperature: Int,
                    val voltage: Int)