package jp.co.yuji.mydebugapplication.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import jp.co.yuji.mydebugapplication.R
import jp.co.yuji.mydebugapplication.domain.model.CommonDto
import jp.co.yuji.mydebugapplication.presentation.presenter.CameraInfoPresenter
import jp.co.yuji.mydebugapplication.presentation.view.adapter.CameraInfoRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_camera_info.view.*
import java.util.*

/**
 * Created by yuji on 2018/01/04.
 */
class CameraInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : Fragment {
            return CameraInfoFragment()
        }
    }

    private val presenter = CameraInfoPresenter()

    private var adapter: CameraInfoRecyclerViewAdapter? = null

    private var progressBar: ProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater!!.inflate(R.layout.fragment_camera_info, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        val list = ArrayList<CommonDto>()
        adapter = CameraInfoRecyclerViewAdapter(activity, list)
        view.recyclerView.adapter = adapter

        progressBar = view.progressBar
        progressBar?.visibility = View.VISIBLE

        addCameraInfo(list)

        return view
    }

    override fun getTitle(): Int {
        return R.string.screen_name_camera_detail
    }

    private fun addCameraInfo(list : ArrayList<CommonDto>) {
        presenter.getCameraInfo(activity, object: CameraInfoPresenter.OnGetCameraInfoListener{
            override fun onGetCameraInfo(cameraList: List<CommonDto>) {
                list.addAll(cameraList)
                if (adapter != null) {
                    adapter?.notifyDataSetChanged()
                    progressBar?.visibility = View.GONE
                }
            }
        })
    }

}