package jp.co.yuji.mydebugapplication.domain.task

import android.annotation.TargetApi
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import java.util.*

/**
 * Get Camera Info Task.
 */
class GetCameraInfoTask(private val context: Context) {

    suspend fun exec(): List<CommonDto> {
        val list = ArrayList<CommonDto>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addCamera2Info(list)
        } else {
            addCamera1Info(list)
        }
        return list
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addCamera2Info(list : ArrayList<CommonDto>) {
        val manager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraIdList = manager.cameraIdList
            for (cameraId in cameraIdList) {
                list.add(CommonDto("camera id", cameraId?.toString().orEmpty()))
                val characteristics = manager.getCameraCharacteristics(cameraId)
                val facing = characteristics.get(CameraCharacteristics.LENS_FACING)
                if (facing != null) {
                    when (facing) {
                        CameraCharacteristics.LENS_FACING_BACK -> list.add(CommonDto("lens facing", "BACK"))
                        CameraCharacteristics.LENS_FACING_FRONT -> list.add(CommonDto("lens facing", "FRONT"))
                        CameraCharacteristics.LENS_FACING_EXTERNAL -> list.add(CommonDto("lens facing", "EXTERNAL"))
                    }
                }
                val map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                if (map != null) {
                    val previewSizes = map.getOutputSizes(SurfaceTexture::class.java)
                    (previewSizes.indices).mapTo(list) {
                        var title = ""
                        if (it == 0) {
                            title = "preview size"
                        }
                        CommonDto(title, previewSizes[it].width.toString() + " X " + previewSizes[it].height.toString())
                    }
                }

            }
        } catch (e : CameraAccessException) {
            println(e.message)
        }
    }

    private fun addCamera1Info(list : ArrayList<CommonDto>) {
        val cameraNumber = Camera.getNumberOfCameras()
        for (i in 0 until cameraNumber) {
            list.add(CommonDto("camera id", i.toString()))
            val cameraInfo = Camera.CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
            list.add(CommonDto("orientation", cameraInfo.orientation.toString()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                list.add(CommonDto("can disable shutter sound", cameraInfo.canDisableShutterSound.toString()))
            }
            when (cameraInfo.facing) {
                Camera.CameraInfo.CAMERA_FACING_BACK -> list.add(CommonDto("camera facing", "BACK"))
                Camera.CameraInfo.CAMERA_FACING_FRONT -> list.add(CommonDto("camera facing", "FRONT"))
            }
            var camera: Camera? = null
            try {
                camera = Camera.open(i)
                val parameters = camera.parameters
                val previewSizes = parameters.supportedPreviewSizes
                val pictureSizes = parameters.supportedPictureSizes
                (0 until previewSizes.size).mapTo(list) {
                    var title = ""
                    if (it == 0) {
                        title = "preview size"
                    }
                    CommonDto(title, previewSizes[it].width.toString() + " X " + previewSizes[it].height.toString())
                }
                (0 until pictureSizes.size).mapTo(list) {
                    var title = ""
                    if (it == 0) {
                        title = "picture size"
                    }
                    CommonDto(title, pictureSizes[it].width.toString() + " X " + pictureSizes[it].height.toString())
                }
            } catch (e: Exception) {
                println(e.message)
            } finally {
                camera?.release()
            }
        }

    }
}